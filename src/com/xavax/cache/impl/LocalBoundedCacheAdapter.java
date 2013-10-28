//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.CacheContext;

/**
 * LocalBoundedCacheAdapter implements a fixed size cache with a least
 * recently used cast out strategy.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the primary key class.
 * @param <V>  the value class.
 */
public class LocalBoundedCacheAdapter<K,V> extends AbstractCacheAdapter<K, V> {

  @Override
  public V get(CacheContext<K,V> context, K key, long time) {
    return null;
  }

  @Override
  public void put(CacheContext<K,V> context, K key, V value, long expires) {    
  }

  @Override
  public void store(K key, V value, long expires) {
  }

  @Override
  public void flush() {
  }

  @Override
  public void remove(CacheContext<K, V> context, K key) {
  }
}
