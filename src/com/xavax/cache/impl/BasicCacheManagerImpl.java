package com.xavax.cache.impl;

import com.xavax.cache.BasicCacheManager;
import com.xavax.cache.CacheContext;

public class BasicCacheManagerImpl<K, V> implements BasicCacheManager<K, V> {

  @Override
  public V get(CacheContext context, K key) {
    return null;
  }

  @Override
  public void put(CacheContext context, K key, V value) {
  }


}
