//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

import com.xavax.info.XProduct;

/**
 * This class defines and provides access to the version information and
 * copyright for XCache.
 */
public class XCache {
  public static XProduct getProduct() {
    return product;
  }

  private final static XProduct product =
      new XProduct("XCache", 1, 0, 0, 2013, 2013, null);
}
