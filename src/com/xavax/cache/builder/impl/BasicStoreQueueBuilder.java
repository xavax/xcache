//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder.impl;

import com.xavax.cache.StoreQueue;
import com.xavax.cache.builder.CacheBuilderException;
import com.xavax.cache.impl.BasicStoreQueue;

/**
 * BasicStoreQueueBuilder provides a fluent interface to build a BasicStoreQueue.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the primary key class.
 * @param <V>  the value class.
 */
public class BasicStoreQueueBuilder<K,V> extends AbstractStoreQueueBuilder<K, V> {

  public final static int DEFAULT_KEEP_ALIVE_TIME = 300;
  public final static int DEFAULT_MIN_THREADS = 4;
  public final static int DEFAULT_MAX_THREADS = 8;
  public final static int DEFAULT_MAX_QUEUE_SIZE = 256;
  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;

  @SuppressWarnings("rawtypes")
  private final static Class<? extends StoreQueue> DEFAULT_STORE_QUEUE_CLASS =
      (Class<? extends StoreQueue>) BasicStoreQueue.class;

  @SuppressWarnings("unchecked")
  public BasicStoreQueueBuilder() {
    super.setStoreQueueClass((Class<? extends StoreQueue<K, V>>) DEFAULT_STORE_QUEUE_CLASS);
  }

  /**
   * Build a store queue.
   *
   * @return a configured store queue.
   */
  @Override
  public BasicStoreQueue<K,V> build() throws CacheBuilderException {
    return (BasicStoreQueue<K,V>) super.build();
  }

  /**
   * Sets the store queue class.
   *
   * @param storeQueueClass  the class of the store queue.
   */
  public BasicStoreQueueBuilder<K, V> withStoreQueueClass(Class<? extends BasicStoreQueue<K,V>> storeQueueClass) {
    super.setStoreQueueClass(storeQueueClass);
    return this;
  }


  /**
   * Sets the keep alive time in seconds for worker threads.
   *
   * @param keepAliveTime  the keep alive time for worker threads.
   */
  public BasicStoreQueueBuilder<K, V> withKeepAliveTime(int keepAliveTime) {
    this.keepAliveTime = keepAliveTime;
    return this;
  }

  /**
   * Sets the load factor of the hash map used to store the data
   * that is in the store queue.
   *
   * @param loadFactor  the hash map load factor.
   */
  public BasicStoreQueueBuilder<K, V> withLoadFactor(float loadFactor) {
    this.loadFactor = loadFactor;
    return this;
  }

  /**
   * Sets the minimum thread count for worker threads.
   *
   * @param minThreads  the minimum number of worker threads.
   */
  public BasicStoreQueueBuilder<K, V> withMinimumThreads(int minThreads) {
    this.minThreads = minThreads > 0 ? minThreads : DEFAULT_MIN_THREADS;
    return this;
  }

  /**
   * Sets the maximum thread count for worker threads. This is also
   * used as the concurrency level of the hash map since it is the
   * maximum number of threads that could be accessing the hash map.
   *
   * @param maxThreads  the maximum number of worker threads.
   */
  public BasicStoreQueueBuilder<K, V> withMaximumThreads(int maxThreads) {
    this.maxThreads = maxThreads > 0 ? maxThreads : DEFAULT_MAX_THREADS;
    return this;
  }

  /**
   * Sets the maximum size of the store queue.
   *
   * @param maxQueueSize  the maximum size of the store queue.
   */
  public BasicStoreQueueBuilder<K, V> withMaximumQueueSize(int maxQueueSize) {
    this.maxQueueSize = maxQueueSize > 0 ? maxQueueSize : DEFAULT_MAX_QUEUE_SIZE;
    return this;
  }

  public int keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;
  public int minThreads = DEFAULT_MIN_THREADS;
  public int maxThreads = DEFAULT_MAX_THREADS;
  public int maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;
  public float loadFactor = DEFAULT_LOAD_FACTOR;
}
