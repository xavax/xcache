//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.CacheAdapter;
import com.xavax.cache.StoreQueue;

public abstract class AbstractCacheAdapter<K, V> implements CacheAdapter<K,V> {

  /**
   * Set the store queue.
   *
   * @param storeQueue  the store queue for this adapter.
   */
  @Override
  public void storeQueue(StoreQueue<K,V> storeQueue) {
    this.storeQueue = storeQueue;
  }

  /**
   * Initialize this cache adapter.
   */
  @Override
  public void start() {
    if ( storeQueue != null ) {
      storeQueue.start();
    }
  }

  private StoreQueue<K,V> storeQueue;
}