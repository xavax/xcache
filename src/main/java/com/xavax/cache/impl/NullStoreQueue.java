//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import com.xavax.cache.StoreQueueEntry;
import com.xavax.cache.builder.impl.NullStoreQueueBuilder;

/**
 * NullStoreQueue does not implement asynchronous writes to the cache
 * and simply passes all store operations directly to the cache adapter.
 *
 * @author alvitar@xavax.com
 *
 * @param <K>  the data type for keys in this cache.
 * @param <V>  the data type for values in this cache.
 */
public class NullStoreQueue<K, V> extends AbstractStoreQueue<K, V> {

  /**
   * Append an entry to the store queue.
   *
   * @param key      the primary key for the cache data.
   * @param value    the data to be stored in the cache.
   * @param expires  the time when the data expires (Java epoch).
   */
  @Override
  public void doStore(K key, V value, long expires) {
    adapter.store(key, value, expires);
  }

  /**
   * Complete a store operation in the store queue, then remove
   * the key and value from the map and return the entry to
   * the free queue.
   * 
   * @param entry the store queue entry.
   */
  public void doComplete(StoreQueueEntry<K,V> entry) {    
  }

  /**
   * Check the store queue for data matching the specified key, or null
   * if no match was found.
   *
   * @param key  the primary key of the cache entry.
   * @return the data matching the specified key.
   */
  @Override
  public StoreQueueEntry<K, V> get(K key) {
    return null;
  }

  /**
   * Return the default builder for this store queue class.
   * 
   * @return the default builder for this store queue class.
   */
  @SuppressWarnings("unchecked")
  @Override
  protected NullStoreQueueBuilder<K, V> exemplar() {
    return (NullStoreQueueBuilder<K, V>) NullStoreQueueBuilder.EXEMPLAR;
  }
}