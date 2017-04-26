//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.impl;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

import com.xavax.base.XObject;
import com.xavax.cache.Positional;
import com.xavax.metrics.TimeMetric;

/**
 * ConcurrentPool manages a pool of resources in a manner that
 * minimizes thread contention.
 *
 * @author alvitar@xavax.com
 *
 * @param <T>  the resource type which must implement Positional.
 */
public abstract class ConcurrentPool<T extends Positional> extends XObject {

  /**
   * Construct a ConcurrentPool with a pool size that is the first power
   * of two that is greater than size.
   *
   * @param size  the suggested pool size.
   */
  public ConcurrentPool(int size, boolean enableMetrics) {
    int poolSize = 2;
    for ( ; poolSize <= size; poolSize *= 2 ) {
    }
    this.poolSize = poolSize;
    this.maxIterations = MAX_CYCLES * poolSize;
    this.lastReleased = new AtomicInteger(0);
    this.resources = new AtomicReferenceArray<T>(poolSize);
    for ( int i = 0; i < poolSize; ++i ) {
      T resource = createResource();
      resource.position(i);
      resources.set(i, resource);
    }
    this.enableMetrics = enableMetrics;
    if ( enableMetrics) {
      allocateTimeMetric = new TimeMetric(SCALE_FACTOR);
      releaseTimeMetric = new TimeMetric(SCALE_FACTOR);
    }
  }

  /**
   * Create a resource to be added to the pool.
   *
   * @return a resource.
   */
  protected abstract T createResource();

  /**
   * Allocate a resource from the pool.
   *
   * @return a resource from the pool.
   */
  public T allocate() {
    T result = null;
    long startTime = 0;
    if ( enableMetrics ) {
      startTime = allocateTimeMetric.currentTime();
    }
    int start = lastReleased.incrementAndGet();
    int i = start & (poolSize - 1);
    for ( int count = 0; count < maxIterations; ++count, ++i ) {
      if ( i >= poolSize ) {
	i = 0;
      }
      T resource = resources.get(i);
      if ( resource != null && resources.compareAndSet(i, resource, null) ) {
	result = resource;
	break;
      }
    }
    if ( enableMetrics ) {
	allocateTimeMetric.addTransaction(startTime);
    }
    return result;
  }

  /**
   * Release a resource back to the pool.
   *
   * @param resource
   */
  public void release(T resource) {
    long startTime = 0;
    if ( enableMetrics ) {
      startTime = releaseTimeMetric.currentTime();
    }
    resources.set(resource.position(), resource);
    if ( enableMetrics ) {
      releaseTimeMetric.addTransaction(startTime);
    }
  }
  /**
   * Returns the metrics for pool allocations.
   *
   * @return the metrics for pool allocations.
   */
  public TimeMetric.Result getAllocationMetrics() {
    return enableMetrics ? allocateTimeMetric.result() : null;
  }

  /**
   * Returns the metrics for pool releases.
   *
   * @return the metrics for pool releases.
   */
  public TimeMetric.Result getReleaseMetrics() {
    return enableMetrics ? releaseTimeMetric.result() : null;
  }

  private final static int MAX_CYCLES = 2;
  private final static long SCALE_FACTOR = TimeMetric.SCALE_BY_MICROSECONDS;

  private final boolean enableMetrics;
  private final int maxIterations;
  private final int poolSize;
  private final AtomicInteger lastReleased;
  private final AtomicReferenceArray<T> resources;
  private TimeMetric allocateTimeMetric;
  private TimeMetric releaseTimeMetric;
}
