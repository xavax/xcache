//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder.impl;

import org.testng.annotations.Test;

import com.xavax.cache.impl.BasicStoreQueue;

import static org.testng.Assert.*;

public class BasicStoreQueueBuilderTest {

  private final static int KEEP_ALIVE_TIME = 600;
  private final static int MIN_THREADS = 8;
  private final static int MAX_THREADS = 16;
  private final static int MAX_QUEUE_SIZE = 512;
  private final static float LOAD_FACTOR = (float) 0.5;

  @SuppressWarnings("unchecked")
  private final static Class<? extends BasicStoreQueue<String,String>> STORE_QUEUE_CLASS =
      (Class<? extends BasicStoreQueue<String,String>>) BasicStoreQueue.class;

  @Test
  public void testBuilder() throws Exception {
    BasicStoreQueueBuilder<String,String> builder = new BasicStoreQueueBuilder<String,String>();
    builder
    	.withStoreQueueClass(STORE_QUEUE_CLASS)
    	.withKeepAliveTime(KEEP_ALIVE_TIME)
    	.withMinimumThreads(MIN_THREADS)
    	.withMaximumThreads(MAX_THREADS)
    	.withMaximumQueueSize(MAX_QUEUE_SIZE)
    	.withLoadFactor(LOAD_FACTOR);
    assertEquals(builder.keepAliveTime, KEEP_ALIVE_TIME);
    assertEquals(builder.minThreads, MIN_THREADS);
    assertEquals(builder.maxThreads, MAX_THREADS);
    assertEquals(builder.maxQueueSize, MAX_QUEUE_SIZE);
    assertEquals(builder.loadFactor, LOAD_FACTOR);
    assertEquals(builder.storeQueueClass, STORE_QUEUE_CLASS);
    BasicStoreQueue<String,String> queue = builder.build();
    assertNotNull(queue);
  }

}
