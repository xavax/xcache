//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder.impl;

import com.xavax.cache.builder.CacheAdapterBuilder;
import com.xavax.cache.builder.StoreQueueBuilder;

/**
 * AbstractCacheAdapterBuilder is the abstract base class for cache adapter builders.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the primary key class.
 * @param <V>  the value class.
 */
public abstract class AbstractCacheAdapterBuilder<K, V> implements CacheAdapterBuilder<K,V> {
  protected StoreQueueBuilder<K,V> storeQueueBuilder;

  /**
   * Construct an AbstractCacheAdapterBuilder
   */
  public AbstractCacheAdapterBuilder() {
    storeQueueBuilder = null;
  }

  /**
   * Set the store queue builder.
   *
   * @param builder  the store queue builder.
   * @return this builder.
   */
  public AbstractCacheAdapterBuilder<K, V> withStoreQueue(StoreQueueBuilder<K,V> builder) {
    storeQueueBuilder = builder;
    return this;
  }
}
