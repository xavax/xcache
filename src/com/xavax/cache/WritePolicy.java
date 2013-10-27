//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * WritePolicy defines how cache writes are propagated along the
 * chain of cache adapters.
 *
 * @author alvitar@xavax.com
 */
public enum WritePolicy {
  /**
   * Write values evicted from this cache to the next cache level.
   */
  WRITE_BACK,

  /**
   * Write values to each cache level.
   */
  WRITE_THROUGH,

  /**
   * Write values only to the last cache level.
   */
  WRITE_TUNNEL
}
