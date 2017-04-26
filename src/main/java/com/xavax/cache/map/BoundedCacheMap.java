//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * BoundedCacheMap is a linked hash map that enforces a maximum map size.
 *
 * @param <K>  the key type for map entries.
 * @param <V>  the value type for map entries.
 *
 * @author alvitar@xavax.com
 */
public class BoundedCacheMap<K, V> extends LinkedHashMap<K, V> {

  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;

  /**
   * Construct a BasicCacheMap
   *
   * @param initialCapacity  the initial capacity of the map.
   * @param loadFactor       the load factor of the map.
   * @param maxEntries       the maximum number of entries to allow in this map.
   */
  public BoundedCacheMap(int initialCapacity, float loadFactor, int maxEntries) {
    super(initialCapacity, loadFactor, true);
    this.maxEntries = maxEntries;
  }

  /**
   * Construct a BasicCacheMap with a default size and load factor.
   *
   * @param maxEntries       the maximum number of entries to allow in this map.
   */
  public BoundedCacheMap(int maxEntries) {
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
