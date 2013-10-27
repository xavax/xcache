//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.base.XObject;
import com.xavax.cache.CacheAdapter;
import com.xavax.cache.StoreQueue;

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

  protected CacheAdapter<K, V> adapter;
}
