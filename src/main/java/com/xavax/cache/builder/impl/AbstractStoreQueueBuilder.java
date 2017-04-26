package com.xavax.cache.builder.impl;

import com.xavax.base.XObject;
import com.xavax.cache.StoreQueue;
import com.xavax.cache.builder.CacheBuilderException;
import com.xavax.cache.builder.StoreQueueBuilder;

public abstract class AbstractStoreQueueBuilder<K, V> extends XObject
	implements StoreQueueBuilder<K, V> {

  public final static boolean DEFAULT_ENABLE_METRICS = false;

  /**
   * Build a store queue.
   *
   * @return a configured store queue.
   */
  @Override
  public StoreQueue<K,V> build() throws CacheBuilderException {
    StoreQueue<K,V> storeQueue = null;
    if ( this.storeQueueClass == null ) {
      throw new CacheBuilderException("null store queue class");
    }
    else {
      try {
	storeQueue = this.storeQueueClass.newInstance();
	if ( storeQueue != null ) {
	  storeQueue.configure(this);
	}
      }
      catch (Exception e) {
	throw new CacheBuilderException("failed to instantiate store queue class " +
	    storeQueueClass.getSimpleName(), e);
      }
    }
    return storeQueue;
  }

  /**
   * Sets the store queue class.
   *
   * @param storeQueueClass  the class of the store queue.
   */
  public void setStoreQueueClass(Class<? extends StoreQueue<K,V>> storeQueueClass) {
    this.storeQueueClass = storeQueueClass;
  }

  /**
   * Enable gathering performance metrics for this store queue.
   *
   * @param enableMetrics  true if performance metrics should be gathered.
   * @return this builder.
   */
  public AbstractStoreQueueBuilder<K, V> withMetrics(boolean enableMetrics) {
    this.enableMetrics = enableMetrics;
    return this;
  }

  public boolean enableMetrics = DEFAULT_ENABLE_METRICS;
  protected Class<? extends StoreQueue<K,V>> storeQueueClass;
}