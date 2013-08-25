package com.xavax.cache;

public interface CacheManager {
  /**
   * Creates and returns a new context for this cache.
   * @return
   */
  public CacheContext createContext();
}
