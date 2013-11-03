//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * CacheMBean provides the interface for monitoring and managing a cache.
 *
 * @author alvitar@xavax.com
 */
public interface CacheMBean {
  /**
   * Return the number of times the cache was accessed since
   * the last time the counters were reset.
   * 
   * @return the number of times the cache was accessed.
   */
  public long getAccessCount();

  /**
   * Return the number of times a cache access resulted in
   * expired data since the last time the counters were reset.
   * 
   * @return the number of times cache data was expired.
   */
  public long getExpireCount();

  /**
   * Return the number of times a cache access resulted in
   * a cache hit since the last time the counters were reset.
   * 
   * @return the number of cache hits.
   */
  public long getHitCount();

  /**
   * Return the number of times a cache access resulted in
   * a cache miss since the last time the counters were reset.
   * 
   * @return the number of cache misses.
   */
  public long getMissCount();

  /**
   * Return the number of times the cache was updated since
   * the last time the counters were reset.
   * 
   * @return the number of times the cache was updated.
   */
  public long getUpdateCount();

  /**
   * Reset the statistical counters.
   */
  public void resetCounters();

  /**
   * Set the countExpiredAsMissed flag.
   *
   * @param countExpiredAsMissed  if true, count expired data as a cache miss.
   */
  public void setCountExpiredAsMissed(boolean countExpiredAsMissed);
}
