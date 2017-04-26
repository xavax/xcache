//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import java.util.Map;

import com.xavax.base.XObject;
import com.xavax.cache.CacheContext;

public abstract class AbstractCacheContext<K,V> extends XObject
    implements CacheContext<K, V> {

  private boolean missed;
  private boolean expired;
  private long expireTime;
  private Map<K, V> cacheMap;

  /**
   * Construct an AbstractCacheContext.
   */
  protected AbstractCacheContext() {
    this.expired = false;
    this.missed = false;
    this.cacheMap = null;
  }

  /**
   * Sets the cache map for this context to the specified map.
   *
   * @param map  the new context cache map.
   */
  @Override
  public void cacheMap(Map<K,V> map) {
    this.cacheMap = map;
  }

  /**
   * Return the cache map for this context.
   *
   * @return the cache map for this context.
   */
  @Override
  public Map<K,V> cacheMap() {
    return this.cacheMap;
  }

  /**
   * Set the expired flag.
   * 
   * @param value the new value of the expired flag.
   */
  public void expired(boolean value) {
    this.expired = value;
  }

  /**
   * @return true if the most recently retrieved cache data has expired.
   */
  public boolean expired() {
    return this.expired;
  }

  /**
   * @return the expireTime for the most recent cache access.
   */
  public long expireTime() {
    return expireTime;
  }

  /**
   * Set the expire time of the most recent cache access.
   * 
   * @param expireTime the expire time in milliseconds (epoch).
   */
  public void expireTime(long expireTime) {
    this.expireTime = expireTime;
  }

  /**
   * Record a cache miss.
   */
  public void miss() {
    this.missed = true;
  }

  /**
   * Set the cache missed flag.
   * 
   * @param missed true if there was a cache miss.
   */
  public void missed(boolean missed) {
    this.missed = missed;
  }

  /**
   * @return true if there was a cache miss.
   */
  public boolean missed() {
    return this.missed;
  }

}
