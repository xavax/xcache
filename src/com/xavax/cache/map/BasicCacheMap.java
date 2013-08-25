package com.xavax.cache.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * BasicCacheMap is a linked hash map that enforces a maximum map size.
 *
 * @param <K>  the key type for map entries.
 * @param <V>  the value type for map entries.
 *
 * @author alvitar@xavax.com
 */
public class BasicCacheMap<K, V> extends LinkedHashMap<K, V> {

  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;

  /**
   * Construct a BasicCacheMap
   *
   * @param initialCapacity  the initial capacity of the map.
   * @param loadFactor       the load factor of the map.
   * @param maxEntries       the maximum number of entries to allow in this map.
   */
  public BasicCacheMap(int initialCapacity, float loadFactor, int maxEntries) {
    super(initialCapacity, loadFactor, true);
    this.maxEntries = maxEntries;
  }

  /**
   * Construct a BasicCacheMap with a default size and load factor.
   *
   * @param maxEntries       the maximum number of entries to allow in this map.
   */
  public BasicCacheMap(int maxEntries) {
    this(maxEntries, DEFAULT_LOAD_FACTOR, maxEntries);
  }

  /**
   * @return true if the current map size exceeds the maximum map entries.
   */
  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return this.size() > this.maxEntries;
  }

  private final static long serialVersionUID = 1L;

  protected int maxEntries;
}
