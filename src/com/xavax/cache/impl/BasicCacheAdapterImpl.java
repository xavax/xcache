//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.CacheAdapter;
import com.xavax.cache.CacheContext;

/**
 * BasicCacheAdapter implements a basic cache with primary key and value.
 * It can be one of many adapters in a chain of responsibility.
 *
 * @param <K>  the primary key class.
 * @param <V>  the value class.
 *
 * @author alvitar@xavax.com
 */
public class BasicCacheAdapterImpl<K,V> implements CacheAdapter<K,V> {

  @Override
  public V get(CacheContext<K,V> context, K key) {
    return null;
  }

  @Override
  public void put(CacheContext<K,V> context, K key, V value, long expires) {    
  }

  @Override
  public void store(K key, V value, long expires) {
  }
}
