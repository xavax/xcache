package com.xavax.cache;

import com.xavax.cache.impl.BasicCacheManagerImpl;

public class BasicCacheBuilder<K,V>
	extends CacheBuilder<Class<? extends BasicCacheAdapter<K,V>>> {

  @SuppressWarnings("unchecked")
  public BasicCacheBuilder() {
    this.managerClass = (Class<? extends BasicCacheManager<K, V>>) BasicCacheManagerImpl.class;
  }

  @SuppressWarnings("unchecked")
  public BasicCacheBuilder<K,V> withAdapter(Class<? extends BasicCacheAdapter<K,V>> adapterClass)
      throws CacheBuilderException {
    return (BasicCacheBuilder<K, V>) super.withAdapter(adapterClass);
  }

  @SuppressWarnings("unchecked")
  public BasicCacheBuilder<K,V> withInitialCapacity(int initialCapacity)
      throws CacheBuilderException {
    return (BasicCacheBuilder<K, V>) super.withInitialCapacity(initialCapacity);
  }

  @SuppressWarnings("unchecked")
  public BasicCacheBuilder<K,V> withMaximumCapacity(int maximumCapacity)
      throws CacheBuilderException {
    return (BasicCacheBuilder<K, V>) super.withMaximumCapacity(maximumCapacity);
  }

  @SuppressWarnings("unchecked")
  public BasicCacheBuilder<K,V> withLoadFactor(float loadFactor)
      throws CacheBuilderException {
    return (BasicCacheBuilder<K, V>) super.withLoadFactor(loadFactor);
  }

  @SuppressWarnings("unchecked")
  public BasicCacheBuilder<K,V> withWritePolicy(WritePolicy writePolicy)
      throws CacheBuilderException {
    return (BasicCacheBuilder<K, V>) super.withWritePolicy(writePolicy);
  }

  public BasicCacheBuilder<K,V> withManager(Class<? extends BasicCacheManager<K,V>> manager)
      throws CacheBuilderException {
    if ( manager == null ) {
      throw new CacheBuilderException("null cache manager class");
    }
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

  Class<? extends BasicCacheManager<K,V>> managerClass;
}
