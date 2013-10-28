//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder;

import com.xavax.cache.CacheAdapter;

public interface CacheAdapterBuilder<K, V> {
  /**
   * Build a cache adapter.
   *
   * @return a configured cache adapter.
   */
  public CacheAdapter<K, V> build() throws CacheBuilderException;
}