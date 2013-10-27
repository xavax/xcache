//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.xavax.cache.impl.LocalBoundedCacheAdapter;
import com.xavax.cache.impl.AbstractCacheManagerImpl;

import static org.testng.Assert.*;

/**
 * Test cases for the BasicCacheBuilder class.
 *
 * @author alvitar@xavax.com
 *
 */
public class CacheBuilderTest {
  private CacheBuilder<String, Integer> builder;

  @BeforeMethod
  public void setup() {
    builder = new CacheBuilder<String, Integer>();
  }

  @SuppressWarnings("unchecked")
  private void setAdapter() throws Exception {
    builder.withAdapter((Class<? extends CacheAdapter<String, Integer>>) LocalBoundedCacheAdapter.class);
  }

  @Test
  public void testConstructor() {
    assertNotNull(builder);
  }

  @Test
  public void testBuild() throws Exception {
    @SuppressWarnings("unchecked")
    CacheManager<String, Integer> manager =
	builder.withManager((Class<? extends CacheManager<String, Integer>>) TestCacheManager.class)
	       .withAdapter((Class<? extends CacheAdapter<String, Integer>>) LocalBoundedCacheAdapter.class)
	       .withInitialCapacity(128)
    	       .withMaximumCapacity(256)
    	       .withLoadFactor((float) 0.5)
    	       .withAdapter((Class<? extends CacheAdapter<String, Integer>>) LocalBoundedCacheAdapter.class)
    	       .withInitialCapacity(1024)
    	       .withMaximumCapacity(2048)
    	       .withLoadFactor((float) 0.75)
    	       .build();
    assertNotNull(manager);
  }

  @Test
  public void testWithAdapter() throws Exception {
    @SuppressWarnings("unchecked")
    Class<? extends CacheAdapter<String, Integer>> adapterClass =
	(Class<? extends CacheAdapter<String, Integer>>) LocalBoundedCacheAdapter.class;
    builder.withAdapter(adapterClass);
    assertNotNull(builder.currentAdapter);
    assertEquals(builder.currentAdapter.adapterClass, adapterClass);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithNullAdapter() throws Exception {
    builder.withAdapter(null);
  }

  @Test
  public void testWithInitialCapacity() throws Exception {
    setAdapter();
    builder.withInitialCapacity(1234);
    assertEquals(builder.currentAdapter.initialCapacity, 1234);
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
    assertEquals(builder.currentAdapter.loadFactor, loadFactor);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithLoadFactorNoAdapter() throws Exception {
    builder.withLoadFactor(0);
  }

  @Test
  public void testWithManager() throws Exception {
    @SuppressWarnings("unchecked")
    Class<? extends CacheManager<String, Integer>> managerClass =
	(Class<? extends CacheManager<String, Integer>>) AbstractCacheManagerImpl.class;
    builder.withManager(managerClass);
    assertEquals(builder.managerClass, managerClass);
  }

  @Test
  public void testWithMaximumCapacity() throws Exception {
    setAdapter();
    builder.withMaximumCapacity(4444);
    assertEquals(builder.currentAdapter.maximumCapacity, 4444);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithMaximumCapacityNoAdapter() throws Exception {
    builder.withMaximumCapacity(4444);
  }

  @Test
  public void testWithWritePolicy() throws Exception {
    setAdapter();
    builder.withWritePolicy(WritePolicy.WRITE_BACK);
    assertEquals(builder.currentAdapter.writePolicy, WritePolicy.WRITE_BACK);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithWritePolicyNoAdapter() throws Exception {
    builder.withWritePolicy(WritePolicy.WRITE_BACK);
  }
}
