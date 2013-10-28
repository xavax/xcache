//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder;

import java.util.List;

import com.xavax.base.XObject;
import com.xavax.cache.CacheAdapter;
import com.xavax.cache.CacheManager;
import com.xavax.cache.WritePolicy;
import com.xavax.util.CollectionFactory;

public class CacheBuilder<K,V> extends XObject {

  protected CacheBuilder() {
    adapters = CollectionFactory.arrayList();
    currentAdapter = null;
  }

  protected CacheBuilder<K,V> withAdapter(Class<? extends CacheAdapter<K,V>> adapterClass) throws CacheBuilderException {
    if ( adapterClass == null ) {
      throw new CacheBuilderException("null cache adapter class");
    }
    AdapterConfiguration<K,V> configuration = new AdapterConfiguration<K,V>(adapterClass);
    this.adapters.add(configuration);
    currentAdapter = configuration;
    return this;
  }

  protected CacheBuilder<K,V> withInitialCapacity(int initialCapacity) throws CacheBuilderException {
    if ( currentAdapter == null ) {
      throw new CacheBuilderException();
    }
    currentAdapter.initialCapacity = initialCapacity;
    return this;
  }

  protected CacheBuilder<K,V> withMaximumCapacity(int maximumCapacity) throws CacheBuilderException {
    if ( currentAdapter == null ) {
      throw new CacheBuilderException();
    }
    currentAdapter.maximumCapacity = maximumCapacity;
    return this;
  }

  protected CacheBuilder<K,V> withLoadFactor(float loadFactor) throws CacheBuilderException {
    if ( currentAdapter == null ) {
      throw new CacheBuilderException();
    }
    currentAdapter.loadFactor = loadFactor;
    return this;
  }

  protected CacheBuilder<K,V> withWritePolicy(WritePolicy writePolicy) throws CacheBuilderException {
    if ( currentAdapter == null ) {
      throw new CacheBuilderException();
    }
    currentAdapter.writePolicy = writePolicy;
    return this;
  }

  public CacheBuilder<K,V> withManager(Class<? extends CacheManager<K,V>> manager)
      throws CacheBuilderException {
    if ( manager == null ) {
      throw new CacheBuilderException("null cache manager class");
    }
    this.managerClass = manager;
    return this;
  }

  public CacheManager<K,V> build() {
    final String method = "build";
    CacheManager<K,V> manager = null;
    try {
      manager = this.managerClass.newInstance();
    }
    catch ( Exception e ) {
      error(method, e, "exception while creating {}", this.managerClass.getName());
    }
    return manager;
  }

  Class<? extends CacheManager<K,V>> managerClass;
  AdapterConfiguration<K,V> currentAdapter;
  List<AdapterConfiguration<K,V>> adapters;
}
