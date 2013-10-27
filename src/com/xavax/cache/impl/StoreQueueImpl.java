//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.xavax.cache.StoreQueue;

/**
 * StoreQueueImpl implements a store queue for a cache adapter allowing
 * asynchronous writes to the cache. The store queue entries preserve the key,
 * value, and expire time until a worker thread is available to perform the
 * cache write operation. Any access to the cache will check for keys in the
 * store queue.
 * 
 * @author alvitar@xavax.com
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 */
public class StoreQueueImpl<K, V> extends AbstractStoreQueue<K, V> implements StoreQueue<K, V> {

  /**
   * Construct a store queue for the designated cache adapter.
   */
  public StoreQueueImpl() {
    this.minThreads = DEFAULT_MIN_THREADS;
    this.maxThreads = DEFAULT_MAX_THREADS;
    this.maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;
    this.loadFactor = DEFAULT_LOAD_FACTOR;
  }

  /**
   * Initialize a store queue by creating the thread pool executor
   * and hash map of data in the queue.
   */
  @PostConstruct
  public void init() {
    if ( maxThreads < minThreads ) {
      maxThreads = minThreads;
    }
    this.map = new ConcurrentHashMap<K, StoreQueueEntry>(maxQueueSize * 2,
	loadFactor, maxThreads);
    this.executor = new ThreadPoolExecutor(minThreads, maxThreads,
	keepAliveTime, TimeUnit.SECONDS,
	new ArrayBlockingQueue<Runnable>(maxQueueSize),
	new ThreadPoolExecutor.CallerRunsPolicy());
  }

  /**
   * Add an entry to the cache store queue.
   * 
   * @param key the primary key for the cache data.
   * @param value the data to be stored in the cache.
   * @param expires the time when the data expires (Java epoch).
   */
  @Override
  public void store(K key, V value, long expires) {
    StoreQueueEntry entry =
	new StoreQueueEntry(this, key, value, expires);
    map.put(key, entry);
    executor.execute(entry);
  }

  /**
   * Execute an entry in the store queue.
   * 
   * @param entry the store queue entry.
   */
  public void execute(StoreQueueEntry entry) {
    adapter.store(entry.key, entry.value, entry.expires);
    map.remove(entry.key, entry);
  }

  /**
   * Check the store queue for data matching the specified key, or null if no
   * match was found.
   * 
   * @param key the primary key of the cache entry.
   * @return the data matching the specified key.
   */
  public V get(K key) {
    StoreQueueEntry entry = map.get(key);
    return entry == null ? null : entry.value;
  }

  /**
   * Sets the minimum thread count for worker threads.
   *
   * @param minThreads  the minimum number of worker threads.
   */
  public void setMinThreads(int minThreads) {
    this.minThreads = minThreads > 0 ? minThreads : DEFAULT_MIN_THREADS;
  }

  /**
   * Sets the maximum thread count for worker threads. This is also
   * used as the concurrency level of the hash map since it is the
   * maximum number of threads that could be accessing the hash map.
   *
   * @param maxThreads  the maximum number of worker threads.
   */
  public void setMaxThreads(int maxThreads) {
    this.maxThreads = maxThreads > 0 ? maxThreads : DEFAULT_MAX_THREADS;
  }

  /**
   * Sets the maximum size of the store queue.
   *
   * @param maxQueueSize  the maximum size of the store queue.
   */
  public void setMaxQueueSize(int maxQueueSize) {
    this.maxQueueSize = maxQueueSize > 0 ? maxQueueSize : DEFAULT_MAX_QUEUE_SIZE;
  }

  /**
   * Sets the load factor of the hash map used to store the data
   * that is in the store queue.
   *
   * @param loadFactor  the hash map load factor.
   */
  public void setLoadFactor(float loadFactor) {
    this.loadFactor = loadFactor;
  }

  private final static int DEFAULT_MIN_THREADS = 4;
  private final static int DEFAULT_MAX_THREADS = 8;
  private final static int DEFAULT_MAX_QUEUE_SIZE = 256;
  private final static float DEFAULT_LOAD_FACTOR = (float) 0.75;

  private int minThreads = 5;
  private int maxThreads = 10;
  private int maxQueueSize = 64;
  private int keepAliveTime = 300;
  private float loadFactor = (float) 0.75;
  private ConcurrentMap<K, StoreQueueEntry> map;
  private ThreadPoolExecutor executor;

  /**
   * StoreQueueEntry encapsulates an entry in the cache store queue and contains
   * all information necessary to complete the cache write transaction.
   */
  public class StoreQueueEntry implements Runnable {

    /**
     * Construct a StoreQueueEntry.
     * 
     * @param queue    the store queue to which this entry is associated.
     * @param key      the primary key for the cache data.
     * @param value    the data to be stored in the cache.
     * @param expires  the time when the data expires (Java epoch).
     */
    public StoreQueueEntry(StoreQueueImpl<K, V> queue, K key, V value,
			   long expires) {
      this.queue = queue;
      this.key = key;
      this.value = value;
      this.expires = expires;
    }

    /**
     * Perform the cache write operation.
     */
    @Override
    public void run() {
      queue.execute(this);
    }

    public final long expires;
    public final K key;
    public final V value;
    public final StoreQueueImpl<K, V> queue;
  }

}
