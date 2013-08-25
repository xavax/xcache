package com.xavax.cache.impl;

import com.xavax.cache.BasicCacheAdapter;
import com.xavax.cache.CacheContext;

public class BasicCacheAdapterImpl<K,V> implements BasicCacheAdapter<K,V> {

  @Override
  public V get(CacheContext context, K key) {
    return null;
  }

  @Override
  public void put(CacheContext context, K key, V value) {    
  }

}
