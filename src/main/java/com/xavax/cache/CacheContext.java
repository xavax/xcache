//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

import java.util.Map;

/**
 * CacheContext is the interface for a generic cache context.
 *
 * @author alvitar@xavax.com
 */
public interface CacheContext<K,V> {
  /**
   * Sets the cache map for this context to the specified map.
   *
   * @param map  the new context cache map.
   */
  public void cacheMap(Map<K,V> map);

  /**
   * Return the cache map for this context.
   *
   * @return the cache map for this context.
   */
  public Map<K,V> cacheMap();

  /**
   * Set the expired flag.
   * 
   * @param value the new value of the expired flag.
   */
  public void expired(boolean value);

  /**
   * @return true if the most recently retrieved cache data has expired.
   */
  public boolean expired();

  /**
   * @return the expireTime for the most recent cache access.
   */
  public long expireTime();

  /**
   * Set the expire time of the most recent cache access.
   * 
   * @param expireTime the expire time in milliseconds (epoch).
   */
  public void expireTime(long expireTime);

  /**
   * Record a cache miss in this context.
   */
  public void miss();

  /**
   * Set the cache missed flag.
   * 
   * @param missed true if there was a cache miss.
   */
  public void missed(boolean missed);

  /**
   * @return true if there was a cache miss.
   */
  public boolean missed();
}
