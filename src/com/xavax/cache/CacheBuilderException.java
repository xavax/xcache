//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * CacheBuilderException is the exception type thrown by cache builders when
 * an error occurs while configuring or building a cache.
 *
 * @author alvitar@xavax.com
 */
public class CacheBuilderException extends Exception {
  public CacheBuilderException(String message, Throwable cause) {
    super(message, cause);
  }

  public CacheBuilderException(String message) {
    super(message);
  }

  public CacheBuilderException() {
    super(DEFAULT_MESSAGE);
  }

  private final static String DEFAULT_MESSAGE =
      "Must specify an adapter class before setting attributes.";
  private final static long serialVersionUID = 1L;
}
