package com.xavax.cache;

import com.xavax.cache.impl.AbstractCacheContext;
import com.xavax.cache.impl.AbstractCacheManagerImpl;

public class TestCacheManager<K,V> extends AbstractCacheManagerImpl<K, V> {

  public class TestContext extends AbstractCacheContext<K,V> {}

  public TestCacheManager() {
  }

  @Override
  public CacheContext<K,V> createContext() {
    return new TestContext();
  }
}