//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * CacheManager is the manager for a multi-level cache.
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

  /**
   * Retrieve the value associated with the specified key from the cache.
   *
   * @param context  the context for this transaction.
   * @param key      the primary key for the cache data.
   * @return the value associated with the specified key.
   */
  public V get(CacheContext<K,V> context, K key);

  /**
   * Insert a value into the cache with the specified key.
   *
   * @param context  the context for this transaction.
   * @param key      the primary key for the cache data.
   * @param value    the data to be stored in the cache.
   * @param expires  the time when the data expires (Java epoch).
   */
  public void put(CacheContext<K,V> context, K key, V value, long expires);

  /**
   * Sets the key class for this cache.
   *
   * @param keyClass  the key class.
   */
  public void keyClass(Class<? extends Object> keyClass);

  /**
   * Returns the key class for this cache.
   *
   * @return the key class for this cache.
   */
  public Class<? extends Object> keyClass();

  /**
   * Sets the value class for this cache.
   *
   * @param valueClass  the value class.
   */
  public void valueClass(Class<? extends Object> valueClass);

  /**
   * Returns the value class for this cache.
   *
   * @return the value class for this cache.
   */
  public Class<? extends Object> valueClass();
}
