//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder;

import com.xavax.base.XObject;
import com.xavax.cache.impl.BasicStoreQueue;

public class BasicStoreQueueBuilder<K,V> extends XObject {

  public final static int DEFAULT_KEEP_ALIVE_TIME = 300;
  public final static int DEFAULT_MIN_THREADS = 4;
  public final static int DEFAULT_MAX_THREADS = 8;
  public final static int DEFAULT_MAX_QUEUE_SIZE = 256;
  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;

  /**
   * Build a store queue.
   *
   * @return a configured store queue.
   */
  public BasicStoreQueue<K,V> build() throws InstantiationException, IllegalAccessException {
    BasicStoreQueue<K,V> storeQueue = null;
    if ( this.storeQueueClass != null ) {
      storeQueue = this.storeQueueClass.newInstance();
    }
    return storeQueue;
  }

  /**
   * Sets the store queue class.
   *
   * @param storeQueueClass  the class of the store queue.
   */
  public BasicStoreQueueBuilder<K,V> withStoreQueueClass(Class<? extends BasicStoreQueue<K,V>> storeQueueClass) {
    this.storeQueueClass = storeQueueClass;
    return this;
  }

  /**
   * Sets the minimum thread count for worker threads.
   *
   * @param minThreads  the minimum number of worker threads.
   */
  public BasicStoreQueueBuilder<K,V> withMinThreads(int minThreads) {
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
  public BasicStoreQueueBuilder<K,V> withMaxThreads(int maxThreads) {
    this.maxThreads = maxThreads > 0 ? maxThreads : DEFAULT_MAX_THREADS;
    return this;
  }

  /**
   * Sets the maximum size of the store queue.
   *
   * @param maxQueueSize  the maximum size of the store queue.
   */
  public BasicStoreQueueBuilder<K,V> withMaxQueueSize(int maxQueueSize) {
    this.maxQueueSize = maxQueueSize > 0 ? maxQueueSize : DEFAULT_MAX_QUEUE_SIZE;
    return this;
  }

  /**
   * Sets the load factor of the hash map used to store the data
   * that is in the store queue.
   *
   * @param loadFactor  the hash map load factor.
   */
  public BasicStoreQueueBuilder<K,V> withLoadFactor(float loadFactor) {
    this.loadFactor = loadFactor;
    return this;
  }

  public int keepAliveTime = DEFAULT_KEEP_ALIVE_TIME;
  public int minThreads = DEFAULT_MIN_THREADS;
  public int maxThreads = DEFAULT_MAX_THREADS;
  public int maxQueueSize = DEFAULT_MAX_QUEUE_SIZE;
  public float loadFactor = DEFAULT_LOAD_FACTOR;
  protected Class<? extends BasicStoreQueue<K,V>> storeQueueClass;
}
