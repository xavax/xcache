//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * StoreQueueMBean provides the interface for monitoring and
 * managing a store queue.
 *
 * @author alvitar@xavax.com
 */
public interface StoreQueueMBean {

  /**
   * Returns the number of store requests that were queued.
   * 
   * @return the number of store requests that were queued.
   */
  public long getQueueCount();

  /**
   * Returns the number of store requests.
   * 
   * @return the number of store requests.
   */
  public long getStoreCount();

  /**
   * Reset the counters.
   */
  public void resetCounters();
}
