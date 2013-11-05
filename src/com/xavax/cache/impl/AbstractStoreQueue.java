//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.xavax.base.XObject;
import com.xavax.cache.CacheAdapter;
import com.xavax.cache.StoreQueue;
import com.xavax.cache.StoreQueueEntry;
import com.xavax.cache.StoreQueueMBean;
import com.xavax.cache.builder.StoreQueueBuilder;
import com.xavax.cache.builder.impl.AbstractStoreQueueBuilder;

/**
 * AbstractStoreQueue is the base class for all store queues.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 */
public abstract class AbstractStoreQueue<K, V> extends XObject
	implements StoreQueue<K, V>, StoreQueueMBean {

  /**
   * Construct an AbstractStoreQueue
   */
  public AbstractStoreQueue() {
  }

  /**
   * Configure this store queue with values from the specified builder.
   *
   * @param builder  the store queue builder containing configuration data.
   */

  @Override
  public void configure(StoreQueueBuilder<K,V> builder) {
    if ( builder instanceof AbstractStoreQueueBuilder ) {
      AbstractStoreQueueBuilder<K,V> asqb = (AbstractStoreQueueBuilder<K,V>) builder;
      this.enableMetrics = asqb.enableMetrics;
    }
  }

  /**
   * Sets the cache adapter associated with this store queue.
   * 
   * @param adapter the cache adapter associated with this store queue.
   */
  public void setAdapter(CacheAdapter<K, V> adapter) {
    this.adapter = adapter;
  }

  /**
   * Initialize a store queue.
   */
  @Override
  public void start(){
    queueCount = new AtomicLong();
    storeCount = new AtomicLong();
    if ( enableMetrics ) {
      completionTimeMetric = new TimeMetric(SCALE_FACTOR);
      requestTimeMetric = new TimeMetric(SCALE_FACTOR);
    }
  }

  /**
   * Returns the number of store requests that were queued.
   * 
   * @return the number of store requests that were queued.
   */
  public long getQueueCount() {
    return queueCount.get();
  }

  /**
   * Returns the number of store requests.
   * 
   * @return the number of store requests.
   */
  public long getStoreCount() {
    return storeCount.get();
  }

  /**
   * Reset the counters.
   */
  public void resetCounters() {
    queueCount.set(0);
    storeCount.set(0);
  }

  /**
   * Returns the metrics for store operations.
   *
   * @return the metrics for store operations.
   */
  public TimeMetric.Result getCompletionMetrics() {
    return enableMetrics ? completionTimeMetric.result() : null;
  }

  /**
   * Returns the metrics for store requests.
   *
   * @return the metrics for store requests.
   */
  public TimeMetric.Result getRequestMetrics() {
    return enableMetrics ? requestTimeMetric.result() : null;
  }

  /**
   * Complete a store operation in the store queue, then remove
   * the key and value from the map and return the entry to
   * the free queue.
   * 
   * @param entry the store queue entry.
   */
  public void complete(StoreQueueEntry<K,V> entry) {
    long startTime = 0;
    if ( enableMetrics ) {
      startTime = System.nanoTime();
    }
    doComplete(entry);
    if ( enableMetrics ) {
      completionTimeMetric.addTransaction(startTime, System.nanoTime());
    }
  }

  /**
   * Complete a store operation in the store queue, then remove
   * the key and value from the map and return the entry to
   * the free queue.
   * 
   * @param entry the store queue entry.
   */
  public abstract void doComplete(StoreQueueEntry<K,V> entry);

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
  public void store(K key, V value, long expires) {
    long startTime = 0;
    if ( enableMetrics ) {
      startTime = System.nanoTime();
    }
    doStore(key, value, expires);
    if ( enableMetrics ) {
      requestTimeMetric.addTransaction(startTime, System.nanoTime());
    }
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
  public abstract void doStore(K key, V value, long expires);

  private final static long SCALE_FACTOR = 100;

  private boolean enableMetrics = true;
  protected AtomicLong storeCount;
  protected AtomicLong queueCount;
  protected CacheAdapter<K, V> adapter;
  private TimeMetric completionTimeMetric;
  private TimeMetric requestTimeMetric;
}
