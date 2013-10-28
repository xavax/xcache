//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder;

import com.xavax.cache.StoreQueue;

public interface StoreQueueBuilder<K, V> {

  /**
   * Build a store queue.
   *
   * @return a configured store queue.
   */
  public abstract StoreQueue<K, V> build() throws CacheBuilderException;

}