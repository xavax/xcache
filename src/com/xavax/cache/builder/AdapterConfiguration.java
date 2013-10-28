//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder;

import com.xavax.cache.CacheAdapter;
import com.xavax.cache.WritePolicy;

/**
 * AdapterConfiguration is a value object by a cache builder's fluent interface
 * to store an adapter's configuration.
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 *
 * @author alvitar@xavax.com
 */
public class AdapterConfiguration<K,V> {
  public final static int DEFAULT_INITIAL_CAPACITY = 512;
  public final static int DEFAULT_MAXIMUM_CAPACITY = 1024;
  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;
  public final static WritePolicy DEFAULT_WRITE_POLICY = WritePolicy.WRITE_THROUGH;

  /**
   * Construct and AdapterConfiguration with the specified adapter class.
   *
   * @param adapterClass  the class of the cache adapter.
   */
  protected AdapterConfiguration(Class<? extends CacheAdapter<K,V>> adapterClass) {
    this.adapterClass = adapterClass;
    this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
    this.maximumCapacity = DEFAULT_MAXIMUM_CAPACITY;
    this.loadFactor = DEFAULT_LOAD_FACTOR;
    this.writePolicy = DEFAULT_WRITE_POLICY;
  }

  public int initialCapacity;
  public int maximumCapacity;
  public float loadFactor;
  public WritePolicy writePolicy;
  public Class<? extends CacheAdapter<K,V>> adapterClass;
}
