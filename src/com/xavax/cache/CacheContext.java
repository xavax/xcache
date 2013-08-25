//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * XCacheContext is the interface for a generic cache context.
 *
 * @author alvitar@xavax.com
 */
public interface CacheContext {
  /**
   * Close this context releasing any resources associated with it.
   */
  public void close();
}
