//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.BasicCacheManager;
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
public class BasicCacheManagerImpl<K, V> implements BasicCacheManager<K, V> {

  @Override
  public V get(CacheContext context, K key) {
    return null;
  }

  @Override
  public void put(CacheContext context, K key, V value) {
  }

}
