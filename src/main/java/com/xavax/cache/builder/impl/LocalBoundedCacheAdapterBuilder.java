//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder.impl;

import com.xavax.cache.builder.CacheBuilderException;
import com.xavax.cache.builder.StoreQueueBuilder;
import com.xavax.cache.impl.LocalBoundedCacheAdapter;

/**
 * LocalBoundedCacheAdapterConfiguration provides a fluent interface for
 * building a cache adapter and also serves as a value object.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 */
public class LocalBoundedCacheAdapterBuilder<K,V> extends AbstractCacheAdapterBuilder<K, V> {
  public final static int DEFAULT_INITIAL_CAPACITY = 512;
  public final static int DEFAULT_MAXIMUM_CAPACITY = 1024;
  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;

  @SuppressWarnings("rawtypes")
  public final static LocalBoundedCacheAdapterBuilder EXEMPLAR = new LocalBoundedCacheAdapterBuilder();

  public int initialCapacity;
  public int maximumCapacity;
  public float loadFactor;

  /**
   * Construct a L with the specified adapter class.
   *
   * @param adapterClass  the class of the cache adapter.
   */
  public LocalBoundedCacheAdapterBuilder() {
    this.adapterClass = null;
    this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
    this.maximumCapacity = DEFAULT_MAXIMUM_CAPACITY;
    this.loadFactor = DEFAULT_LOAD_FACTOR;
  }

  /**
   * Build a cache adapter.
   *
   * @return a configured cache adapter.
   * @throws CacheBuilderException 
   */
  @Override
  public LocalBoundedCacheAdapter<K,V> build() throws CacheBuilderException {
    return (LocalBoundedCacheAdapter<K,V>) super.build();
  }

  /**
   * Set the adapter class.
   *
   * @param adapterClass  the adapter class.
   * @return this builder.
   */
  public LocalBoundedCacheAdapterBuilder<K,V>
  withAdapterClass(Class<? extends LocalBoundedCacheAdapter<K,V>> adapterClass) {
    super.setAdapterClass(adapterClass);
    return this;
  }

  /**
   * Set the adapter name.
   *
   * @param name  the adapter name.
   * @return this builder.
   */
  public LocalBoundedCacheAdapterBuilder<K,V> withName(String name) {
    return (LocalBoundedCacheAdapterBuilder<K,V>) super.withName(name);
  }

  /**
   * Set the store queue builder.
   *
   * @param builder  the store queue builder.
   * @return this builder.
   */
  public LocalBoundedCacheAdapterBuilder<K,V>
  withStoreQueue(StoreQueueBuilder<K,V> builder) {
    return (LocalBoundedCacheAdapterBuilder<K,V>) super.withStoreQueue(builder);
  }

  /**
   * Set the initial capacity of the cache map.
   *
   * @param initialCapacity  the initial capacity of the cache map.
   * @return this builder.
   */
  protected LocalBoundedCacheAdapterBuilder<K,V> withInitialCapacity(int initialCapacity) {
    this.initialCapacity = initialCapacity;
    return this;
  }

  /**
   * Set the maximum capacity of the cache map.
   *
   * @param maximumCapacity  the maximum capacity of the cache map.
   * @return this builder.
   */
  protected LocalBoundedCacheAdapterBuilder<K,V> withMaximumCapacity(int maximumCapacity) {
    this.maximumCapacity = maximumCapacity;
    return this;
  }

  /**
   * Set the load factor of the cache map.
   *
   * @param loadFactor  the load factor of the cache map.
   * @return this builder.
   */
  protected LocalBoundedCacheAdapterBuilder<K,V> withLoadFactor(float loadFactor) {
    this.loadFactor = loadFactor;
    return this;
  }

}
