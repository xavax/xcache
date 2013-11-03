//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * CacheAdapterMBean provides the interface for monitoring and
 * managing a cache adapter.
 *
 * @author alvitar@xavax.com
 */
public interface CacheAdapterMBean extends CacheMBean {

  /**
   * Returns the cache level of this cache adapter.
   * 
   * @return the cache level of this cache adapter.
   */
  public int getLevel();

  /**
   * Returns the name of this cache adapter.
   *
   * @return the name of this cache adapter.
   */
  public String getName();

  /**
   * Return the number of times a cache access resulted in a
   * store queue hit since the last time the counters were reset.
   * 
   * @return the number of cache hits.
   */
  public long getStoreQueueHitCount();
}
