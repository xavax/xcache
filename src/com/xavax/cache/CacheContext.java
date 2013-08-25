package com.xavax.cache;

/**
 * XCacheContext is the interface for a generic cache context.
 *
 * @author alvitar@xavax.com
 */
public interface CacheContext {
  /**
   * Close this context releasing any resources associated with it.
   */
  public void close();
}
