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
@SuppressWarnings("serial")
public class CacheMapEntry<K, V> extends MapEntry {

  /**
   * Construct a CacheMapEntry.
   */
  public CacheMapEntry() {
    initialize(null, null, 0);
  }

  /**
   * Initialize a CacheMapEntry with the specified key, value, and expire time.
   *
   * @param key         the primary key for this entry.
   * @param value       the value for this entry.
   * @param expireTime  the expire time in milliseconds (Java epoch) for this entry.
   */
  public void initialize(K key, V value, long expireTime) {
    this.key = key;
    this.value = value;
    this.expireTime = expireTime;
  }

  /**
   * Set the key for this entry.
   *
   * @param key  the key for this entry.
   */
  public void key(K key) {
    this.key = key;
  }

  /**
   * Return the primary key for this entry.
   *
   * @return the primary key for this entry.
   */
  public K key() {
    return this.key;
  }

  /**
   * Set the value for this entry.
   *
   * @param value  the value for this entry.
   */
  public void value(V value) {
    this.value = value;
  }

  /**
   * Return the value for this entry.
   *
   * @return the value for this entry.
   */
  public V value() {
    return this.value;
  }

  /**
   * Set the expire time for this entry.
   *
   * @param expireTime  the expire time for this entry.
   */
  public void expireTime(long expireTime) {
    this.expireTime = expireTime;
  }

  /**
   * Return the expire time for this entry.
   *
   * @return the expire time for this entry.
   */
  public long expireTime() {
    return this.expireTime;
  }

  private long expireTime;
  private K key;
  private V value;
}
