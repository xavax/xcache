//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

public interface BasicCacheAdapter<K,V> {
  public V get(CacheContext context, K key);

  public void put(CacheContext context, K key, V value);  
}
