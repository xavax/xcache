//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.CacheManager;
import com.xavax.cache.CacheContext;

/**
 * BasicCacheManagerImpl implements a cache manager for a chain of cache
 * adapters which support a primary key and value.
 *
 * @param <K>  the primary key class.
 * @param <V>  the value class.
 *
 * @author alvitar@xavax.com
 */
public abstract class AbstractCacheManagerImpl<K, V> implements CacheManager<K, V> {

  @Override
  public V get(CacheContext<K,V> context, K key) {
    return null;
  }

  @Override
  public void put(CacheContext<K,V> context, K key, V value) {
  }

}
