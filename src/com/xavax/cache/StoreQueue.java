//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * StoreQueue implements a store queue for a cache adapter allowing asynchronous
 * writes to the cache.
 * 
 * @author alvitar@xavax.com
 */
public interface StoreQueue<K,V> {

  /**
   * Append an entry to the store queue.
   *
   * @param key      the primary key for the cache data.
   * @param value    the data to be stored in the cache.
   * @param expires  the time when the data expires (Java epoch).
   */
  public void store(K key, V value, long expires);

  /**
   * Check the store queue for data matching the specified key, or null
   * if no match was found.
   *
   * @param key  the primary key of the cache entry.
   * @return the data matching the specified key.
   */
  public V get(K key);
}
