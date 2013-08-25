//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.map;

/**
 * BasicCacheMapEntry is an entry in a basic cache and stores the
 * primary key, value, and expire time for this entry.
 *
 * @param <PK>  the primary key class.
 * @param <V>   the value class.
 *
 * @author alvitar@xavax.com
 */
public class BasicCacheMapEntry<PK, V> {

  /**
   * Construct a BasicCacheMapEntry with the specified key, value, and expire time.
   *
   * @param primaryKey  the primary key for this entry.
   * @param value       the value for this entry.
   * @param expireTime  the expire time in seconds for this entry.
   */
  public BasicCacheMapEntry(PK primaryKey, V value, int expireTime) {
    this.primaryKey = primaryKey;
    this.value = value;
    this.expireTime = expireTime;
  }

  /**
   * @return the primary key for this entry.
   */
  public PK primaryKey() {
    return this.primaryKey;
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
  public int expireTime() {
    return this.expireTime;
  }

  private int expireTime;
  private PK  primaryKey;
  private V   value;
}
