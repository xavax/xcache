//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.base.XObject;
import com.xavax.cache.CacheAdapter;
import com.xavax.cache.StoreQueue;
import com.xavax.cache.builder.StoreQueueBuilder;

/**
 * AbstractStoreQueue is the base class for all store queues.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 */
public abstract class AbstractStoreQueue<K, V> extends XObject
	implements StoreQueue<K, V> {

  /**
   * Construct an AbstractStoreQueue
   */
  public AbstractStoreQueue() {
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
   * Configure this store queue with values from the specified builder.
   *
   * @param builder  the store queue builder containing configuration data.
   */
  @Override
  public void configure(StoreQueueBuilder<K,V> builder) {
  }

  /**
   * Initialize a store queue.
   */
  @Override
  public void start(){
  }

  protected CacheAdapter<K, V> adapter;
}
