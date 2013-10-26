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

import com.sun.org.apache.xpath.internal.objects.XObject;
import com.xavax.cache.CacheAdapter;
import com.xavax.cache.StoreQueue;

/**
 * StoreQueueImpl implements a store queue for a cache adapter allowing
 * asynchronous writes to the cache. The store queue entries preserve the key,
 * value, and expire time until a worker thread is available to perform the
 * cache write operation.
 * 
 * @author alvitar@xavax.com
 */
public class StoreQueueImpl<K, V> extends XObject implements StoreQueue<K, V> {

  private final static int STORE_QUEUE_MIN_THREADS = 5;
  private final static int STORE_QUEUE_MAX_THREADS = 10;
  private final static int STORE_QUEUE_MAX_SIZE = 64;
  private final static int STORE_QUEUE_KEEP_ALIVE = 300;
  private final static float STORE_QUEUE_LOAD_FACTOR = (float) 0.75;

  /**
   * Construct a store queue for the designated cache adapter.
   * 
   * @param adapter the adapter cache associated with this store queue.
   */
  public StoreQueueImpl(CacheAdapter<K,V> adapter) {
    this.adapter = adapter;
    this.map = new ConcurrentHashMap<K,StoreQueueEntry>(STORE_QUEUE_MAX_SIZE * 2,
	STORE_QUEUE_LOAD_FACTOR, STORE_QUEUE_MAX_THREADS);
    this.executor = new ThreadPoolExecutor(STORE_QUEUE_MIN_THREADS,
	STORE_QUEUE_MAX_THREADS, STORE_QUEUE_KEEP_ALIVE, TimeUnit.SECONDS,
	new ArrayBlockingQueue<Runnable>(STORE_QUEUE_MAX_SIZE),
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
   * @param entry  the store queue entry.
   */
  public void execute(StoreQueueEntry entry) {
    adapter.store(entry.key, entry.value, entry.expires);
    map.remove(entry.key, entry);
  }

  /**
   * Check the store queue for data matching the specified key, or null
   * if no match was found.
   *
   * @param key  the primary key of the cache entry.
   * @return the data matching the specified key.
   */
  public V get(K key) {
    StoreQueueEntry entry = map.get(key);
    return entry == null ? null : entry.value;
  }

  private static final long serialVersionUID = 1L;

  private final CacheAdapter<K,V> adapter;
  private final ConcurrentMap<K,StoreQueueEntry> map;
  private final ThreadPoolExecutor executor;

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
      public StoreQueueEntry(StoreQueueImpl<K,V> queue, K key, V value, long expires) {
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
      public final StoreQueueImpl<K,V> queue;
  }

}
