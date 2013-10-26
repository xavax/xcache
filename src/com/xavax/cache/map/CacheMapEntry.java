//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.map;

/**
 * CacheMapEntry is an entry in a cache and stores the primary key, value, and
 * expire time for this entry.
 *
 * @param <K>  the primary key class.
 * @param <V>   the value class.
 *
 * @author alvitar@xavax.com
 */
public class CacheMapEntry<K, V> {

  /**
   * Construct a BasicCacheMapEntry with the specified key, value, and expire time.
   *
   * @param key         the primary key for this entry.
   * @param value       the value for this entry.
   * @param expireTime  the expire time in milliseconds (Java epoch) for this entry.
   */
  public CacheMapEntry(K key, V value, long expireTime) {
    this.key = key;
    this.value = value;
    this.expireTime = expireTime;
  }

  /**
   * @return the primary key for this entry.
   */
  public K key() {
    return this.key;
  }

  /**
   * @return the value for this entry.
   */
  public V value() {
    return this.value;
  }

  /**
   * @return the expire time for this entry.
   */
  public long expireTime() {
    return this.expireTime;
  }

  private long expireTime;
  private K key;
  private V value;
}
