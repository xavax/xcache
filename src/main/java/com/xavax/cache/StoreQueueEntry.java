//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * StoreQueueEntry encapsulates an entry in the cache store queue and contains
 * all information necessary to complete the cache write transaction.
 */
public class StoreQueueEntry<K,V> implements Runnable, Positional {
  /**
   * Construct a StoreQueueEntry for the specified store queue.
   */
  public StoreQueueEntry(StoreQueue<K,V> storeQueue) {
    this.storeQueue = storeQueue;
  }

  /**
   * Construct a StoreQueueEntry that is a copy of entry.
   *
   * @param entry  the store queue entry to copy.
   */
  public StoreQueueEntry(StoreQueueEntry<K,V> entry) {
    synchronized ( entry ) {
      this.storeQueue = entry.storeQueue;
      this.key = entry.key;
      this.value = entry.value;
      this.expires = entry.expires;
    }
  }

  /**
   * Initialize a store queue entry.
   *
   * @param key      the primary key for the cache data.
   * @param value    the data to be stored in the cache.
   * @param expires  the time when the data expires (Java epoch).
   */
  public synchronized void init(K key, V value, long expires) {
    this.key = key;
    this.value = value;
    this.expires = expires;
  }

  /**
   * Clear a store queue entry.
   */
  public synchronized void clear() {
    this.key = null;
    this.value = null;
    this.expires = 0;
  }

  /**
   * Return the primary key for this store queue entry.
   *
   * @return the primary key.
   */
  public K key() {
    return key;
  }

  /**
   * Return the value for this store queue entry.
   *
   * @return the value.
   */
  public V value() {
    return value;
  }

  /**
   * Return the expire time in milliseconds (Java epoch).
   *
   * @return the expire time.
   */
  public long expires() {
    return expires;
  }

  /**
   * Perform the cache write operation.
   */
  @Override
  public void run() {
    storeQueue.complete(this);
  }

  /* (non-Javadoc)
   * @see com.xavax.cache.HasSlotNumber#slot(int)
   */
  @Override
  public void position(int slot) {
    this.slot = slot;
  }

  /* (non-Javadoc)
   * @see com.xavax.cache.HasSlotNumber#slot()
   */
  @Override
  public int position() {
    return this.slot;
  }

  private int slot;
  private long expires;
  private K key;
  private V value;
  private StoreQueue<K,V> storeQueue;
}