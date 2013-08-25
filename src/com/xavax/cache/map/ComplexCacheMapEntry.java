//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.map;

/**
 * ComplexCacheMapEntry is an entry in a complex cache and stores the
 * primary key, secondary key, value, and expire time for this entry.
 *
 * @param <PK>  the primary key class.
 * @param <SK>  the secondary key class.
 * @param <V>   the value class.
 *
 * @author alvitar@xavax.com
 */
public class ComplexCacheMapEntry<PK, SK, V> extends BasicCacheMapEntry<PK, V> {

  /**
   * Construct a ComplexCacheMapEntry with the specified keys, value, and expire time.
   *
   * @param primaryKey    the primary key for this entry.
   * @param secondaryKey  the secondary key for this entry.
   * @param value         the value for this entry.
   * @param expireTime    the expire time in seconds for this entry.
   */
  public ComplexCacheMapEntry(PK primaryKey, SK secondaryKey, V value, int expireTime) {
    super(primaryKey, value, expireTime);
    this.secondaryKey = secondaryKey;
  }

  /**
   * @return the secondary key for this entry.
   */
  public SK secondaryKey() {
    return this.secondaryKey;
  }

  private SK  secondaryKey;
}
