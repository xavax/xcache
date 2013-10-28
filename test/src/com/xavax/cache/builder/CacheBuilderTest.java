//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.xavax.cache.CacheAdapter;
import com.xavax.cache.CacheManager;
import com.xavax.cache.ExampleCacheManager;
import com.xavax.cache.WritePolicy;
import com.xavax.cache.builder.CacheBuilder;
import com.xavax.cache.builder.CacheBuilderException;
import com.xavax.cache.impl.LocalBoundedCacheAdapter;
import com.xavax.cache.impl.AbstractCacheManagerImpl;

import static org.testng.Assert.*;

/**
 * Test cases for the CacheBuilder class.
 *
 * @author alvitar@xavax.com
 */
public class CacheBuilderTest {
  private CacheBuilder<String, Integer> builder;

  @BeforeMethod
  public void setup() {
    builder = new CacheBuilder<String, Integer>();
  }

  @Test
  public void testConstructor() {
    assertNotNull(builder);
  }

  @Test
  public void testBuild() throws Exception {
    @SuppressWarnings("unchecked")
    CacheManager<String, Integer> manager =
	builder.withManager((Class<? extends CacheManager<String, Integer>>) ExampleCacheManager.class)
		.build();
    assertNotNull(manager);
  }

  @Test
  public void testWithAdapter() throws Exception {
    @SuppressWarnings("unchecked")
    Class<? extends CacheAdapter<String, Integer>> adapterClass =
	(Class<? extends CacheAdapter<String, Integer>>) LocalBoundedCacheAdapter.class;
//    builder.withAdapter(adapterClass);
//    assertNotNull(builder.currentAdapter);
//    assertEquals(builder.currentAdapter.adapterClass, adapterClass);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithNullAdapter() throws Exception {
    builder.withAdapter(null);
  }

  @Test
  public void testWithManager() throws Exception {
    @SuppressWarnings("unchecked")
    Class<? extends CacheManager<String, Integer>> managerClass =
	(Class<? extends CacheManager<String, Integer>>) AbstractCacheManagerImpl.class;
    builder.withManager(managerClass);
    assertEquals(builder.managerClass, managerClass);
  }

  @Test(expectedExceptions = CacheBuilderException.class)
  public void testWithWritePolicyNoAdapter() throws Exception {
    builder.withWritePolicy(WritePolicy.WRITE_BACK);
  }
}
