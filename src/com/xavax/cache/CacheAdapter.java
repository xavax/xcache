//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * CacheAdapter is the interface for all cache adapters in XCache.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 */
public interface CacheAdapter<K,V> {
  /**
   * Flush the cache content managed by this cache adapter.
   */
  public void flush();

  /**
   * Access a value in this cache.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the data requested.
   * @return the value matching the key, or null if not found.
   */
  public V get(CacheContext<K,V> context, K key);

  /**
   * Insert a value into this cache with the specified key.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the value to be inserted.
   * @param value    the value to be inserted.
   * @param expires  the expire time of the data in milliseconds (Java epoch).
   */
  public void put(CacheContext<K,V> context, K key, V value, long expires);

  /**
   * Remove the specified content from this cache.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the content to be removed.
   */
  public void remove(CacheContext<K,V> context, K key);

  /**
   * Perform the store operation. This is only called by the store queue.
   *
   * @param key      the primary key of the value to be inserted.
   * @param value    the value to be inserted.
   * @param expires  the expire time of the data in milliseconds (Java epoch).
   */
  public void store(K key, V value, long expires);
}
