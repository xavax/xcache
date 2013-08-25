package com.xavax.cache;

import java.util.List;
import java.util.Map;

import com.xavax.base.XObject;
import com.xavax.util.CollectionFactory;

public class BasicCacheBuilder<K,V> extends XObject {

  public BasicCacheBuilder() {
    adapters = CollectionFactory.arrayList();
    initialContent = null;
    current = null;
  }

  public BasicCacheBuilder<K,V> withInitialCapacity(int initialCapacity)
      throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.initialCapacity = initialCapacity;
    return this;
  }

  public BasicCacheBuilder<K,V> withMaximumCapacity(int maximumCapacity)
      throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.maximumCapacity = maximumCapacity;
    return this;
  }

  public BasicCacheBuilder<K,V> withLoadFactor(float loadFactor)
      throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.loadFactor = loadFactor;
    return this;
  }

  public BasicCacheBuilder<K,V> withWritePolicy(WritePolicy writePolicy)
      throws CacheBuilderException {
    if ( current == null ) {
      throw new CacheBuilderException();
    }
    current.writePolicy = writePolicy;
    return this;
  }

  public BasicCacheBuilder<K,V> withAdapter(Class<? extends BasicCacheAdapter<K,V>> adapterClass) {
    current = new BasicAdapterConfiguration<K,V>(adapterClass);
    this.adapters.add(current);
    return this;
  }

  public BasicCacheBuilder<K,V> withManager(Class<? extends BasicCacheManager<K,V>> manager) {
    this.managerClass = manager;
    return this;
  }

  public BasicCacheManager<K,V> build() {
    final String method = "build";
    BasicCacheManager<K,V> manager = null;
    try {
      manager = this.managerClass.newInstance();
    }
    catch ( Exception e ) {
      error(method, e, "exception while creating {}", this.managerClass.getName());
    }
    return manager;
  }

  BasicAdapterConfiguration<K,V> current;
  List<BasicAdapterConfiguration<K,V>> adapters;
  Class<? extends BasicCacheManager<K,V>> managerClass;
  Map<K,V> initialContent;
}
