package com.xavax.cache;

import com.xavax.cache.impl.AbstractCacheContext;
import com.xavax.cache.impl.AbstractCacheManagerImpl;

/**
 * ExampleCacheManager is a simple implementation of CacheManager
 * used for testing.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the primary key type.
 * @param <V>  the value type.
 */
public class ExampleCacheManager<K,V> extends AbstractCacheManagerImpl<K, V> {

  public class ExampleContext extends AbstractCacheContext<K,V> {}

  public ExampleCacheManager() {
  }

  @Override
  public CacheContext<K,V> createContext() {
    return new ExampleContext();
  }
}