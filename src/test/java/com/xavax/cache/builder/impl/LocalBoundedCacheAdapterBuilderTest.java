//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder.impl;

import org.testng.annotations.Test;

import com.xavax.cache.impl.BasicStoreQueue;
import com.xavax.cache.impl.LocalBoundedCacheAdapter;

import static org.testng.Assert.*;

public class LocalBoundedCacheAdapterBuilderTest {

  private final static int KEEP_ALIVE_TIME = 600;
  private final static int MIN_THREADS = 8;
  private final static int MAX_THREADS = 16;
  private final static int MAX_QUEUE_SIZE = 512;
  private final static int INITIAL_CAPACITY = 1024;
  private final static int MAXIMUM_CAPACITY = 2048;
  private final static float LOAD_FACTOR = (float) 0.5;

  @SuppressWarnings("unchecked")
  private final static Class<? extends LocalBoundedCacheAdapter<String,String>> ADAPTER_CLASS =
      (Class<? extends LocalBoundedCacheAdapter<String,String>>) LocalBoundedCacheAdapter.class;
  @SuppressWarnings("unchecked")
  private final static Class<? extends BasicStoreQueue<String,String>> STORE_QUEUE_CLASS =
      (Class<? extends BasicStoreQueue<String,String>>) BasicStoreQueue.class;
  private final static BasicStoreQueueBuilder<String, String> storeQueueBuilder =
      new BasicStoreQueueBuilder<String, String>();

  @Test
  public void testBuilder() throws Exception {
    LocalBoundedCacheAdapterBuilder<String, String> builder =
	new LocalBoundedCacheAdapterBuilder<String, String>();
    builder
	.withAdapterClass(ADAPTER_CLASS)
	.withStoreQueue(storeQueueBuilder
	    .withStoreQueueClass(STORE_QUEUE_CLASS)
	    .withKeepAliveTime(KEEP_ALIVE_TIME)
	    .withMinimumThreads(MIN_THREADS)
	    .withMaximumThreads(MAX_THREADS)
	    .withMaximumQueueSize(MAX_QUEUE_SIZE)
	    .withLoadFactor(LOAD_FACTOR))
	.withInitialCapacity(INITIAL_CAPACITY)
	.withMaximumCapacity(MAXIMUM_CAPACITY)
	.withLoadFactor(LOAD_FACTOR);
    assertEquals(builder.adapterClass, ADAPTER_CLASS);
    assertEquals(builder.initialCapacity, INITIAL_CAPACITY);
    assertEquals(builder.maximumCapacity, MAXIMUM_CAPACITY);
    assertEquals(builder.loadFactor, LOAD_FACTOR);
    assertEquals(builder.storeQueueBuilder, storeQueueBuilder);
    LocalBoundedCacheAdapter<String,String> adapter = builder.build();
    assertNotNull(adapter);
  }

  @Test
  public void testNoStoreQueue() throws Exception {
    LocalBoundedCacheAdapterBuilder<String, String> builder =
	new LocalBoundedCacheAdapterBuilder<String, String>();
    builder
	.withAdapterClass(ADAPTER_CLASS)
	.withInitialCapacity(INITIAL_CAPACITY)
	.withMaximumCapacity(MAXIMUM_CAPACITY)
	.withLoadFactor(LOAD_FACTOR);
    assertEquals(builder.adapterClass, ADAPTER_CLASS);
    assertEquals(builder.initialCapacity, INITIAL_CAPACITY);
    assertEquals(builder.maximumCapacity, MAXIMUM_CAPACITY);
    assertEquals(builder.loadFactor, LOAD_FACTOR);
    assertNull(builder.storeQueueBuilder);
    LocalBoundedCacheAdapter<String,String> adapter = builder.build();
    assertNotNull(adapter);
  }
}
