//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xavax.cache.StoreQueueEntry;
import com.xavax.cache.builder.StoreQueueBuilder;
import com.xavax.cache.builder.impl.BasicStoreQueueBuilder;

/**
 * BasicStoreQueue implements a store queue for a cache adapter allowing
 * asynchronous writes to the cache. The store queue entries preserve the key,
 * value, and expire time until a worker thread is available to perform the
 * cache write operation. Any access to the cache will check for keys in the
 * store queue.
 *
 * There are several objects involved.
 * - A store queue entry encapsulates a key, value, and expire time.
 *   To minimize object creation overhead, a fixed number of store queue
 *   entries are created and reused.
 * - A concurrent hash map stores data waiting in the queue. It is used
 *   as a look-aside cache.
 * - There are separate queues for busy and free store queue entries.
 * - A thread pool executor manages the working threads that write data
 *   to the cache. As each entry is dispatched, the associated data is
 *   removed from the hash map and the entry returned to the free queue.
 * 
 * @author alvitar@xavax.com
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 */
public class BasicStoreQueue<K, V> extends AbstractStoreQueue<K, V> {

  /**
   * Construct a store queue for the designated cache adapter.
   */
  public BasicStoreQueue() {
    BasicStoreQueueBuilder<K,V> builder = new BasicStoreQueueBuilder<K,V>();
    configure(builder);
  }

  /**
   * Configure this store queue with values from the specified builder.
   *
   * @param builder  the store queue builder containing configuration data.
   */
  @Override
  public void configure(StoreQueueBuilder<K,V> builder) {
    super.configure(builder);
    if ( builder instanceof BasicStoreQueueBuilder ) {
      BasicStoreQueueBuilder<K,V> bsqb = (BasicStoreQueueBuilder<K,V>) builder;
      this.keepAliveTime = bsqb.keepAliveTime;
      this.minThreads    = bsqb.minThreads;
      this.maxThreads    = bsqb.maxThreads;
      this.maxQueueSize  = bsqb.maxQueueSize;
      this.loadFactor    = bsqb.loadFactor;
    }
  }

  /**
   * Initialize a store queue by creating the thread pool executor,
   * separate queues for free and busy store queue entries, and a
   * hash map of data waiting in the queue.
   */
  @Override
  public void start() {
    super.start();
    if ( maxThreads < minThreads ) {
      maxThreads = minThreads;
    }
    int freeQueueSize = maxQueueSize + (FREE_QUEUE_MULTIPLIER * maxThreads);
    busyQueue = new LinkedBlockingQueue<Runnable>(maxQueueSize);
    freeQueue = new LinkedBlockingQueue<StoreQueueEntry<K,V>>(freeQueueSize);
    for ( int i = 0; i < freeQueueSize; ++i ) {
      freeQueue.add(new StoreQueueEntry<K,V>(this));
    }
    map = new ConcurrentHashMap<K, StoreQueueEntry<K,V>>(maxQueueSize * MAP_MULTIPLIER,
	loadFactor, maxThreads);
    executor = new ThreadPoolExecutor(minThreads, maxThreads,
	keepAliveTime, TimeUnit.SECONDS, busyQueue,
	new ThreadPoolExecutor.CallerRunsPolicy());
  }

  /**
   * If the store queue is full, immediately perform the write
   * (caller runs); otherwise, add an entry to the store queue
   * and insert the key and value into the map.
   * 
   * @param key the primary key for the cache data.
   * @param value the data to be stored in the cache.
   * @param expires the time when the data expires (Java epoch).
   */
  @Override
  public void doStore(K key, V value, long expires) {
    StoreQueueEntry<K,V> entry = this.freeQueue.poll();
    if ( entry == null ) {
      adapter.store(key, value, expires);
    }
    else {
      entry.init(key, value, expires);
      map.put(key, entry);
      executor.execute(entry);
      queueCount.incrementAndGet();
    }
    storeCount.incrementAndGet();
  }

  /**
   * Complete a store operation in the store queue, then remove
   * the key and value from the map and return the entry to
   * the free queue.
   * 
   * @param entry the store queue entry.
   */
  public void doComplete(StoreQueueEntry<K,V> entry) {
    K key = entry.key();
    adapter.store(key, entry.value(), entry.expires());
    map.remove(key, entry);
    entry.clear();
    freeQueue.add(entry);
  }

  /**
   * Check the store queue for data matching the specified key, or null if no
   * match was found.
   * 
   * @param key the primary key of the cache entry.
   * @return the data matching the specified key.
   */
  public StoreQueueEntry<K,V> get(K key) {
    StoreQueueEntry<K,V> entry = map.get(key);
    return entry == null ? null : new StoreQueueEntry<K,V>(entry);
  }

  private final static int FREE_QUEUE_MULTIPLIER = 2;
  private final static int MAP_MULTIPLIER = 2;

  private int minThreads;
  private int maxThreads;
  private int maxQueueSize;
  private int keepAliveTime;
  private float loadFactor;
  private BlockingQueue<Runnable> busyQueue;
  private BlockingQueue<StoreQueueEntry<K,V>> freeQueue;
  private ConcurrentMap<K, StoreQueueEntry<K,V>> map;
  private ThreadPoolExecutor executor;
}
