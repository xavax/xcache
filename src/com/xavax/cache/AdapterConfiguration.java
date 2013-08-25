//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * AdapterConfiguration is a value object by a cache builder's fluent interface
 * to store an adapter's configuration.
 *
 * @param <T>  the adapter class.
 *
 * @author alvitar@xavax.com
 */
public class AdapterConfiguration<T> {
  public final static int DEFAULT_INITIAL_CAPACITY = 512;
  public final static int DEFAULT_MAXIMUM_CAPACITY = 1024;
  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;
  public final static WritePolicy DEFAULT_WRITE_POLICY = WritePolicy.WRITE_THROUGH;

  /**
   * Construct and AdapterConfiguration with the specified adapter class.
   *
   * @param adapterClass  the class of the cache adapter.
   */
  protected AdapterConfiguration(T adapterClass) {
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
  public T adapterClass;
}
