//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.CacheAdapter;
import com.xavax.cache.CacheManager;
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
public abstract class AbstractCacheManagerImpl<K, V> implements CacheManager<K, V> {

  /**
   * Retrieve the value associated with the specified key from the cache.
   *
   * @param context  the context for this transaction.
   * @param key      the primary key for the cache data.
   * @return the value associated with the specified key.
   */
  @Override
  public V get(CacheContext<K,V> context, K key) {
    V result = null;
    long time = System.currentTimeMillis();
    for ( CacheAdapter<K,V> adapter : adapters ) {
      result = adapter.get(context, key, time);
      if ( result != null ) {
	break;
      }
    }
    return result;
  }

  @Override
  public void put(CacheContext<K,V> context, K key, V value, long expires) {
  }

  /**
   * Sets the key class for this cache adapter.
   *
   * @param keyClass  the key class.
   */
  @Override
  public void keyClass(Class<? extends Object> keyClass) {
    this.keyClass = keyClass;
  }

  /**
   * Returns the key class for this cache adapter.
   *
   * @return the key class for this cache adapter.
   */
  @Override
  public Class<? extends Object> keyClass() {
    return this.keyClass;
  }

  /**
   * Sets the value class for this cache adapter.
   *
   * @param valueClass  the value class.
   */
  @Override
  public void valueClass(Class<? extends Object> valueClass) {
    this.valueClass = valueClass;
  }

  /**
   * Returns the value class for this cache adapter.
   *
   * @return the value class for this cache adapter.
   */
  @Override
  public Class<? extends Object> valueClass() {
    return this.valueClass;
  }

  protected Class<? extends Object> keyClass;
  protected Class<? extends Object> valueClass;
  protected CacheAdapter<K,V>[] adapters;

}
