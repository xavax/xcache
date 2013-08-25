package com.xavax.cache;

public class BasicAdapterConfiguration<K, V> {
  public final static int DEFAULT_INITIAL_CAPACITY = 512;
  public final static int DEFAULT_MAXIMUM_CAPACITY = 1024;
  public final static float DEFAULT_LOAD_FACTOR = (float) 0.75;
  public final static WritePolicy DEFAULT_WRITE_POLICY = WritePolicy.WRITE_THROUGH;

  public BasicAdapterConfiguration(Class<? extends BasicCacheAdapter<K, V>> adapterClass) {
    this.adapterClass = adapterClass;
    this.initialCapacity = DEFAULT_INITIAL_CAPACITY;
    this.maximumCapacity = DEFAULT_MAXIMUM_CAPACITY;
    this.loadFactor = DEFAULT_LOAD_FACTOR;
    this.writePolicy = DEFAULT_WRITE_POLICY;
  }

  int initialCapacity;
  int maximumCapacity;
  float loadFactor;
  WritePolicy writePolicy;
  Class<? extends BasicCacheAdapter<K, V>> adapterClass;
}
