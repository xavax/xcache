//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * CacheManager is the base class for all cache managers.
 *
 * @author alvitar@xavax.com
 */
public interface CacheManager {
  /**
   * Creates and returns a new context for this cache.
   *
   * @return a new context for this cache.
   */
  public CacheContext createContext();
}
