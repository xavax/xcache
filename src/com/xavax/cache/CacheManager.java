//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * CacheManager is the manager for a cache.
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 * 
 * @author alvitar@xavax.com
 */
public interface CacheManager<K,V> {
  /**
   * Creates and returns a new context for this cache.
   *
   * @return a new context for this cache.
   */
  public CacheContext<K,V> createContext();

  public V get(CacheContext<K,V> context, K key);

  public void put(CacheContext<K,V> context, K key, V value);  
}
