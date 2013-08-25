package com.xavax.cache;

public interface BasicCacheAdapter<K,V> {
  public V get(CacheContext context, K key);

  public void put(CacheContext context, K key, V value);  
}
