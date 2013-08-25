package com.xavax.cache;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.xavax.cache.impl.BasicCacheAdapterImpl;
import com.xavax.cache.impl.BasicCacheManagerImpl;

import static org.testng.Assert.*;

public class BasicCacheBuilderTest {
  private BasicCacheBuilder<String, Integer> builder;

  @BeforeMethod
  public void setup() {
    builder = new BasicCacheBuilder<String, Integer>();
  }

  @SuppressWarnings("unchecked")
  private void setAdapter() throws Exception {
    builder.withAdapter((Class<? extends BasicCacheAdapter<String, Integer>>) BasicCacheAdapterImpl.class);
  }

  @Test
  public void testConstructor() {
    assertNotNull(builder);
  }

  @Test
  public void testBuild() throws Exception {
    @SuppressWarnings("unchecked")
    Class<? extends BasicCacheAdapter<String, Integer>> adapterClass =
	(Class<? extends BasicCacheAdapter<String, Integer>>) BasicCacheAdapterImpl.class;
    @SuppressWarnings("unchecked")
    Class<? extends BasicCacheManager<String, Integer>> managerClass =
	(Class<? extends BasicCacheManager<String, Integer>>) BasicCacheManagerImpl.class;
    BasicCacheManager<String, Integer> manager =
	builder.withManager(managerClass)
	       .withAdapter(adapterClass)
	       .withInitialCapacity(128)
    	       .withMaximumCapacity(256)
    	       .withLoadFactor((float) 0.5)
    	       .withAdapter(adapterClass)
    	       .withInitialCapacity(1024)
    	       .withMaximumCapacity(2048)
    	       .withLoadFactor((float) 0.75)
    	       .build();
    assertNotNull(manager);
  }

  @Test
  public void testWithAdapter() throws Exception {
    @SuppressWarnings("unchecked")
    Class<? extends BasicCacheAdapter<String, Integer>> adapterClass =
	(Class<? extends BasicCacheAdapter<String, Integer>>) BasicCacheAdapterImpl.class;
    builder.withAdapter(adapterClass);
    assertNotNull(builder.current);
    assertEquals(builder.current.adapterClass, adapterClass);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithNullAdapter() throws Exception {
    builder.withAdapter(null);
  }

  @Test
  public void testWithInitialCapacity() throws Exception {
    setAdapter();
    builder.withInitialCapacity(1234);
    assertEquals(builder.current.initialCapacity, 1234);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithInitialCapacityNoAdapter() throws Exception {
    builder.withInitialCapacity(1234);
  }

  @Test
  public void testWithLoadFactor() throws Exception {
    setAdapter();
    float loadFactor = (float) 0.50;
    builder.withLoadFactor(loadFactor);
    assertEquals(builder.current.loadFactor, loadFactor);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithLoadFactorNoAdapter() throws Exception {
    builder.withLoadFactor(0);
  }

  @Test
  public void testWithManager() throws Exception {
    @SuppressWarnings("unchecked")
    Class<? extends BasicCacheManager<String, Integer>> managerClass =
	(Class<? extends BasicCacheManager<String, Integer>>) BasicCacheManagerImpl.class;
    builder.withManager(managerClass);
    assertEquals(builder.managerClass, managerClass);
  }

  @Test
  public void testWithMaximumCapacity() throws Exception {
    setAdapter();
    builder.withMaximumCapacity(4444);
    assertEquals(builder.current.maximumCapacity, 4444);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithMaximumCapacityNoAdapter() throws Exception {
    builder.withMaximumCapacity(4444);
  }

  @Test
  public void testWithWritePolicy() throws Exception {
    setAdapter();
    builder.withWritePolicy(WritePolicy.WRITE_BACK);
    assertEquals(builder.current.writePolicy, WritePolicy.WRITE_BACK);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithWritePolicyNoAdapter() throws Exception {
    builder.withWritePolicy(WritePolicy.WRITE_BACK);
  }
}
