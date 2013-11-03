//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.test;

/**
 * XCacheTestCase is a base class for XCache test cases.
 *
 * @author alvitar@xavax.com
 */
public class XCacheTestCase {

  /**
   * Sleep the specified number of milliseconds.
   *
   * @param interval  the amount of time in milliseconds to sleep.
   */
  public static void sleep(long interval) {
    try {
      Thread.currentThread().wait(interval);
    }
    catch (Exception e) {
      // Ignore this exception.
    }
  }
}