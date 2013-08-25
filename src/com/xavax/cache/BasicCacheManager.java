//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * XBasicCacheManager is the manager for single level caches.
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 * 
 * @author alvitar@xavax.com
 */
public interface BasicCacheManager<K, V> {

  public V get(CacheContext context, K key);

  public void put(CacheContext context, K key, V value);  
}
