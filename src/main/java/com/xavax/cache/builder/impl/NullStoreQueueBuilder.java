//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder.impl;

import com.xavax.cache.StoreQueue;
import com.xavax.cache.builder.CacheBuilderException;
import com.xavax.cache.impl.NullStoreQueue;

/**
 * NullStoreQueueBuilder builds a NullStoreQueue.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the primary key class.
 * @param <V>  the value class.
 */
public class NullStoreQueueBuilder<K,V> extends AbstractStoreQueueBuilder<K, V> {

  @SuppressWarnings("rawtypes")
  public final static NullStoreQueueBuilder EXEMPLAR = new NullStoreQueueBuilder();

  /**
   * Build a store queue.
   *
   * @return a configured store queue.
   */
  @Override
  public StoreQueue<K, V> build() throws CacheBuilderException {
    return new NullStoreQueue<K,V>();
  }

}
