package com.xavax.cache;

import java.util.List;
import java.util.Map;

/**
 * XComplexCacheManager is the manager for two level caches with both
 * a primary and secondary key.
 *
 * @param <PK>  the data type for primary keys in this cache.
 * @param <SK>  the data type for secondary keys in this cache.
 * @param <V>  the data type for values in this cache.
 * 
 * @author alvitar@xavax.com
 */
public interface ComplexCacheManager<PK, SK, V> extends CacheManager {
  public V get(CacheContext context, PK primaryKey, SK secondaryKey);

  public Map<SK, V> get(CacheContext context, PK primaryKey);

  public List<V> get(CacheContext context, PK primaryKey, List<SK> secondaryKeys);

  public void put(CacheContext context, PK primaryKey, SK secondaryKey, V value);

  public void put(CacheContext context, PK primaryKey, Map<SK, V> values);
}
