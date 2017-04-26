//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder.impl;

import com.xavax.cache.CacheAdapter;
import com.xavax.cache.StoreQueue;
import com.xavax.cache.builder.CacheAdapterBuilder;
import com.xavax.cache.builder.CacheBuilderException;
import com.xavax.cache.builder.StoreQueueBuilder;

/**
 * AbstractCacheAdapterBuilder is the abstract base class for cache adapter builders.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the primary key class.
 * @param <V>  the value class.
 */
public abstract class AbstractCacheAdapterBuilder<K, V> implements CacheAdapterBuilder<K,V> {

  /**
   * Construct an AbstractCacheAdapterBuilder
   */
  public AbstractCacheAdapterBuilder() {
    this.name = null;
    this.adapterClass = null;
    this.storeQueueBuilder = null;
  }

  /**
   * Build a cache adapter.
   *
   * @return a configured cache adapter.
   * @throws CacheBuilderException 
   */
  @Override
  public CacheAdapter<K,V> build() throws CacheBuilderException {
    CacheAdapter<K,V> adapter = null;
    if ( this.adapterClass == null ) {
      throw new CacheBuilderException("null adapter class");
    }
    else {
      try {
	adapter = this.adapterClass.newInstance();
	if (adapter != null) {
	  adapter.configure(this);
	  if ( storeQueueBuilder == null ) {
	    storeQueueBuilder = new NullStoreQueueBuilder<K,V>();
	  }
	  StoreQueue<K, V> storeQueue = storeQueueBuilder.build();
	  storeQueue.configure(storeQueueBuilder);
	  storeQueue.setAdapter(adapter);
	  adapter.storeQueue(storeQueue);
	}
      }
      catch (Exception e) {
	throw new CacheBuilderException("failed to instantiate adapter class " +
	    adapterClass.getSimpleName(), e);
      }
    }
    return adapter;
  }

  /**
   * Set the adapter class.
   *
   * @param adapterClass  the adapter class.
   * @return this builder.
   */
  public void setAdapterClass(Class<? extends CacheAdapter<K,V>> adapterClass) {
    this.adapterClass = adapterClass;
  }

  /**
   * Set the store queue builder.
   *
   * @param builder  the store queue builder.
   * @return this builder.
   */
  public AbstractCacheAdapterBuilder<K, V> withStoreQueue(StoreQueueBuilder<K,V> builder) {
    this.storeQueueBuilder = builder;
    return this;
  }

  /**
   * Set the adapter name.
   *
   * @param name  the adapter name.
   * @return this builder.
   */
  public AbstractCacheAdapterBuilder<K, V> withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Return the name of this cache adapter.
   *
   * @return the name of this cache adapter.
   */
  public String name() {
    return this.name;
  }

  /**
   * Return the store queue builder.
   *
   * @return the store queue builder.
   */
  public StoreQueueBuilder<K,V> storeQueueBuilder() {
    return this.storeQueueBuilder;
  }

  /**
   * Return the class of this cache adapter.
   *
   * @return the class of this cache adapter.
   */
  public Class<? extends CacheAdapter<K,V>> adapterClass() {
    return this.adapterClass;
  }

  private String name;
  protected StoreQueueBuilder<K,V> storeQueueBuilder;
  protected Class<? extends CacheAdapter<K,V>> adapterClass;
}
