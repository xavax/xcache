package com.xavax.cache;

import java.util.List;

import com.xavax.base.XObject;
import com.xavax.util.CollectionFactory;

public class CacheBuilder<T> extends XObject {

  protected CacheBuilder() {
    adapters = CollectionFactory.arrayList();
    current = null;
  }

  protected CacheBuilder<T> withAdapter(T adapterClass) throws CacheBuilderException {
    if ( adapterClass == null ) {
      throw new CacheBuilderException("null cache adapter class");
    }
    AdapterConfiguration<T> configuration = new AdapterConfiguration<>(adapterClass);
    this.adapters.add(configuration);
    current = configuration;
    return this;
  }

  protected CacheBuilder<T> withInitialCapacity(int initialCapacity) throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.initialCapacity = initialCapacity;
    return this;
  }

  protected CacheBuilder<T> withMaximumCapacity(int maximumCapacity) throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.maximumCapacity = maximumCapacity;
    return this;
  }

  protected CacheBuilder<T> withLoadFactor(float loadFactor) throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.loadFactor = loadFactor;
    return this;
  }

  protected CacheBuilder<T> withWritePolicy(WritePolicy writePolicy) throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.writePolicy = writePolicy;
    return this;
  }

  AdapterConfiguration<T> current;
  List<AdapterConfiguration<T>> adapters;
}
