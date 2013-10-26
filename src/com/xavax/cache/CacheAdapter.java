//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

public interface CacheAdapter<K,V> {
  public V get(CacheContext<K,V> context, K key);

  public void put(CacheContext<K,V> context, K key, V value, long expireTime);

  public void store(K key, V value, long expires);
}
