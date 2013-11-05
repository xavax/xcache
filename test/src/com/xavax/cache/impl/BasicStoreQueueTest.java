//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.xavax.cache.CacheContext;
import com.xavax.cache.builder.CacheAdapterBuilder;
import com.xavax.cache.builder.impl.BasicStoreQueueBuilder;
import com.xavax.cache.test.XCacheTestCase;

import static org.testng.Assert.*;

public class BasicStoreQueueTest extends XCacheTestCase {
  private static final int QUEUE_SIZE = 1024;
  private static final int MIN_THREADS = 4;
  private static final int MAX_THREADS = 8;
  public static final int WORKER_THREAD_COUNT = 16;
  public static final int DATA_SET_SIZE = 64 * 1024 * WORKER_THREAD_COUNT;
  public static final int WORKER_DELAY_TIME = 0;
  public static final int WRITER_DELAY_TIME = 0;

  @BeforeMethod
  public void setup() throws Exception {
    adapter = new TestCacheAdapter<Integer, Integer>();
    BasicStoreQueueBuilder<Integer,Integer> builder =
	new BasicStoreQueueBuilder<Integer, Integer>();
    storeQueue = builder
	.withMetrics(true)
	.withMinimumThreads(MIN_THREADS)
	.withMaximumThreads(MAX_THREADS)
    	.withMaximumQueueSize(QUEUE_SIZE).build();
    adapter.storeQueue(storeQueue);
    adapter.start();
  }

  @Test
  public void testBasicStoreQueue() throws Exception {
    Worker[] worker = new Worker[WORKER_THREAD_COUNT];
    for ( int i = 0; i < WORKER_THREAD_COUNT; ++i ) {
      worker[i] = new Worker(i, WORKER_THREAD_COUNT);
    }
    long time0 = System.currentTimeMillis();
    for ( int i = 0; i < WORKER_THREAD_COUNT; ++i ) {
      worker[i].start();
    }
    for ( int i = 0; i < WORKER_THREAD_COUNT; ++i ) {
      worker[i].join();
    }
    long time1 = System.currentTimeMillis();
    for ( int i = 0; i < DATA_SET_SIZE; ++i ) {
      Integer key = new Integer(i);
      assertEquals(key, dataMap[i]);
    }
    long elapsed = time1 - time0;
    double average = ((double) elapsed) / ((double) DATA_SET_SIZE);
    long storeCount = storeQueue.getStoreCount();
    long queueCount = storeQueue.getQueueCount();
    double eff = ((double) queueCount / (double) storeCount) * 100;
    TimeMetric.Result requestMetrics = storeQueue.getRequestMetrics();
    TimeMetric.Result completionMetrics = storeQueue.getCompletionMetrics();
    System.out.println("elapsed time: " + elapsed +
	"\naverage time: " + average +
	"\nstore count: " + storeCount +
	"\nqueue count: " + queueCount +
	"\neffectiveness: " + eff + "%" +
	"\nrequest: " + requestMetrics.toString() +
	"\ncompletion: " + completionMetrics.toString());
  }

  private BasicStoreQueue<Integer, Integer> storeQueue;
  private TestCacheAdapter<Integer, Integer> adapter;
  private Integer[] dataMap = new Integer[DATA_SET_SIZE];

  public class TestCacheAdapter<K, V> extends AbstractCacheAdapter<K, V> {

    @Override
    public void flush() {}

    @Override
    public V doGet(CacheContext<K, V> context, K key, long time) {
      return null;
    }

    @Override
    public void remove(CacheContext<K, V> context, K key) {
    }

    @Override
    public void store(K key, V value, long expires) {
      BasicStoreQueueTest.sleep(WORKER_DELAY_TIME);
      Integer i = ((Integer) key);
      int index = i.intValue();
      dataMap[index] = i;
    }

    @Override
    public void configure(CacheAdapterBuilder<K, V> builder) {
    }
  }

  public class Worker extends Thread {
    public Worker(int offset, int stride) {
      this.offset = offset;
      this.stride = stride;
    }

    @Override
    public void run() {
      for ( int i = offset; i < DATA_SET_SIZE; i += stride ) {
	Integer key = new Integer(i);
	adapter.put(null, key, key, 0);
	BasicStoreQueueTest.sleep(WORKER_DELAY_TIME);
      }
    }

    public String toString() {
      return "[" + this.getName() + ", " + offset + "]";
    }

    private int offset;
    private int stride;
  }
}
