//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.xavax.cache.CacheAdapter;
import com.xavax.cache.CacheAdapterMBean;
import com.xavax.cache.CacheContext;
import com.xavax.cache.StoreQueue;
import com.xavax.cache.StoreQueueEntry;
import com.xavax.cache.builder.CacheAdapterBuilder;
import com.xavax.cache.builder.impl.AbstractCacheAdapterBuilder;

public abstract class AbstractCacheAdapter<K, V>
	implements CacheAdapter<K,V>, CacheAdapterMBean {

  /**
   * Construct an AbstractCacheAdapter.
   */
  public AbstractCacheAdapter() {
    accessCounter = new AtomicLong();
    expireCounter = new AtomicLong();
    hitCounter = new AtomicLong();
    missCounter = new AtomicLong();
    updateCounter = new AtomicLong();
    storeQueueHitCounter = new AtomicLong();
  }

  /**
   * Return the number of times the cache was accessed since
   * the last time the counters were reset.
   * 
   * @return the number of times the cache was accessed.
   */
  public long getAccessCount() {
    return accessCounter.get();
  }

  /**
   * Return the number of times a cache access resulted in
   * expired data since the last time the counters were reset.
   * 
   * @return the number of times cache data was expired.
   */
  public long getExpireCount() {
    return expireCounter.get();
  }

  /**
   * Return the number of times a cache access resulted in
   * a cache hit since the last time the counters were reset.
   * 
   * @return the number of cache hits.
   */
  public long getHitCount() {
    return hitCounter.get();
  }

  /**
   * Return the number of times a cache access resulted in
   * a cache miss since the last time the counters were reset.
   * 
   * @return the number of cache misses.
   */
  public long getMissCount() {
    return missCounter.get();
  }

  /**
   * Return the number of times the cache was updated since
   * the last time the counters were reset.
   * 
   * @return the number of times the cache was updated.
   */
  public long getUpdateCount() {
    return updateCounter.get();
  }

  /**
   * Reset the statistical counters.
   */
  public void resetCounters() {
    accessCounter.set(0);
    expireCounter.set(0);
    hitCounter.set(0);
    missCounter.set(0);
    updateCounter.set(0);
    storeQueueHitCounter.set(0);
  }

  /**
   * Set the countExpiredAsMissed flag.
   *
   * @param countExpiredAsMissed  if true, count expired data as a cache miss.
   */
  public void setCountExpiredAsMissed(boolean countExpiredAsMissed) {
    this.countExpiredAsMissed = countExpiredAsMissed;
  }
  /**
   * Return the number of times a cache access resulted in a
   * store queue hit since the last time the counters were reset.
   * 
   * @return the number of cache hits.
   */
  public long getStoreQueueHitCount() {
    return storeQueueHitCounter.get();
  }

  /**
   * Returns the cache level of this cache adapter.
   * 
   * @return the cache level of this cache adapter.
   */
  public int getLevel() {
    return level;
  }

  /**
   * Returns the name of this cache adapter.
   *
   * @return the name of this cache adapter.
   */
  public String getName() {
    return name;
  }

  /**
   * Register a cache hit for this adapter.
   */
  protected void hit() {
    accessCounter.incrementAndGet();
    hitCounter.incrementAndGet();
  }

  /**
   * Register a store queue hit for this adapter.
   */
  protected void storeQueueHit() {
    accessCounter.incrementAndGet();
    storeQueueHitCounter.incrementAndGet();
  }

  /**
   * Register a cache miss for this adapter.
   */
  protected void miss() {
    accessCounter.incrementAndGet();
    missCounter.incrementAndGet();
  }

  /**
   * Register a hit that resulted in expired data.
   */
  protected void expired() {
    accessCounter.incrementAndGet();
    if ( countExpiredAsMissed ) {
      missCounter.incrementAndGet();
    }
    else {
      hitCounter.incrementAndGet();
    }
  }

  /**
   * Access a value in this cache.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the data requested.
   * @param time     the current time in milliseconds (Java epoch).
   * @return the value matching the key, or null if not found.
   */
  public V get(CacheContext<K,V> context, K key, long time) {
    V result = null;
    if ( storeQueue != null ) {
      StoreQueueEntry<K,V> entry = storeQueue.get(key);
      if ( entry != null && time < entry.expires() ) {
	result = entry.value();
	storeQueueHit();
      }
    }
    if ( result == null ) {
      result = doGet(context, key, time);
    }
    return result;
  }

  /**
   * Access a value in this cache.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the data requested.
   * @param time     the current time in milliseconds (Java epoch).
   * @return the value matching the key, or null if not found.
   */
  protected abstract V doGet(CacheContext<K,V> context, K key, long time);

  /**
   * Insert a value into this cache with the specified key.
   *
   * @param context  the context for this cache access.
   * @param key      the primary key of the value to be inserted.
   * @param value    the value to be inserted.
   * @param expires  the expire time of the data in milliseconds (Java epoch).
   */
  @Override
  public void put(CacheContext<K, V> context, K key, V value, long expires) {
    updateCounter.incrementAndGet();
    if ( storeQueue == null ) {
      store(key, value, expires);
    }
    else {
      storeQueue.store(key, value, expires);
    }
  }

  /**
   * Set the store queue.
   *
   * @param storeQueue  the store queue for this adapter.
   */
  @Override
  public void storeQueue(StoreQueue<K,V> storeQueue) {
    this.storeQueue = storeQueue;
  }

  /**
   * Initialize this cache adapter.
   */
  @Override
  public void start() {
    if ( storeQueue != null ) {
      storeQueue.setAdapter(this);
      storeQueue.start();
    }
  }

  /**
   * Configure this cache adapter with values from the specified builder.
   *
   * @param builder  the builder containing configuration data.
   */
  @Override
  public void configure(CacheAdapterBuilder<K,V> builder) {
    if ( builder instanceof AbstractCacheAdapterBuilder ) {
      this.name = ((AbstractCacheAdapterBuilder<K,V>) builder).name();
    }
  }

  /**
   * Return the exemplar for this class.
   */
  public AbstractCacheAdapterBuilder<K,V> exemplar() {
    return null;
  }

  protected boolean configured = false;
  private boolean countExpiredAsMissed;
  private int level;
  private String name;
  private AtomicLong accessCounter;
  private AtomicLong expireCounter;
  private AtomicLong hitCounter;
  private AtomicLong missCounter;
  private AtomicLong updateCounter;
  private AtomicLong storeQueueHitCounter;
  private StoreQueue<K,V> storeQueue;
}
