//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.CacheContext;
import com.xavax.cache.builder.CacheAdapterBuilder;
import com.xavax.cache.builder.impl.LocalBoundedCacheAdapterBuilder;
import com.xavax.cache.map.BoundedCacheMap;
import com.xavax.cache.map.CacheMapEntry;

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

  /**
   * Construct a LocalBoundedCacheAdapter.
   */
  public LocalBoundedCacheAdapter() {
  }

  /**
   * Flush the cache content managed by this cache adapter.
   */
  @Override
  public void flush() {
    map.clear();
  }

  /**
   * Access a value in this cache.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the data requested.
   * @param time     the current time in milliseconds (Java epoch).
   * @return the value matching the key, or null if not found.
   */
  @Override
  protected V doGet(CacheContext<K,V> context, K key, long time) {
    V result = null;
    CacheMapEntry<K,V> entry = map.get(key);
    if ( entry != null ) {
      if ( entry.expireTime() > time ) {
	context.expired();
      }
      else {
	result = entry.value();
      }
    }
    return result;
  }

  /**
   * Insert a value into this cache with the specified key.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the value to be inserted.
   * @param value    the value to be inserted.
   * @param expires  the expire time of the data in milliseconds (Java epoch).
   */
  @Override
  public void put(CacheContext<K,V> context, K key, V value, long expires) {
    CacheMapEntry<K,V> entry = new CacheMapEntry<K,V>(key, value, expires);
    map.put(key, entry);
  }

  /**
   * Remove the specified content from this cache.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the content to be removed.
   */
  @Override
  public void remove(CacheContext<K,V> context, K key) {
    map.remove(key);
  }

  /**
   * Perform the store operation. This is only called by the store queue.
   *
   * @param key      the primary key of the value to be inserted.
   * @param value    the value to be inserted.
   * @param expires  the expire time of the data in milliseconds (Java epoch).
   */
  @Override
  public void store(K key, V value, long expires) {
  }

  /**
   * Configure this cache adapter with values from the specified builder.
   *
   * @param builder  the builder containing configuration data.
   */
  @Override
  public void configure(CacheAdapterBuilder<K,V> builder) {
    if ( builder instanceof LocalBoundedCacheAdapterBuilder ) {
      LocalBoundedCacheAdapterBuilder<K,V> lbcab = (LocalBoundedCacheAdapterBuilder<K,V>) builder;
      this.initialCapacity = lbcab.initialCapacity;
      this.maximumCapacity = lbcab.maximumCapacity;
      this.loadFactor = lbcab.loadFactor;
    }
  }

  /**
   * Initialize this cache adapter.
   */
  @Override
  public void start() {
    if ( !configured ) {
      this.configure(exemplar());
    }
    assert(initialCapacity > 0 && maximumCapacity >= initialCapacity && loadFactor > 0);
    super.start();
    map = new BoundedCacheMap<K,CacheMapEntry<K,V>>(initialCapacity, loadFactor, maximumCapacity);
  }

  /**
   * Return the exemplar for this class.
   */
  @SuppressWarnings("unchecked")
  public LocalBoundedCacheAdapterBuilder<K,V> exemplar() {
    return (LocalBoundedCacheAdapterBuilder<K,V>) LocalBoundedCacheAdapterBuilder.EXEMPLAR;
  }

  private int initialCapacity;
  private int maximumCapacity;
  private float loadFactor;
  private BoundedCacheMap<K,CacheMapEntry<K,V>> map;
}
