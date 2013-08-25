package com.xavax.cache;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test case for BasicCacheMap.
 *
 * @author alvitar@xavax.com
 */
public class BasicCacheMapTest {

  private final static int INITIAL_CAPACITY = 512;
  private final static int MAX_ENTRIES = 1024;
  private final static float LOAD_FACTOR = (float) 0.50;

  private BasicCacheMap<Integer, Integer> map;

  @Test
  public void testConstructorWithParams() {
    map = new BasicCacheMap<Integer, Integer>(INITIAL_CAPACITY, LOAD_FACTOR, MAX_ENTRIES);
    assertNotNull(map);
  }

  @Test
  public void testConstructorWithDefaults() {
    map = new BasicCacheMap<Integer, Integer>(MAX_ENTRIES);
    assertNotNull(map);
  }

  @Test
  public void testRemoveEldestEntry() {
    map = new BasicCacheMap<Integer, Integer>(MAX_ENTRIES);
    int i = 0;
    for ( ; i < MAX_ENTRIES; ++i ) {
      map.put(i, i);
    }
    assertEquals(map.size(), MAX_ENTRIES);
    map.put(i, i);
    assertEquals(map.size(), MAX_ENTRIES);
    assertNull(map.get(0));
    map.get(1);
    ++i;
    map.put(i, i);
    assertEquals(map.size(), MAX_ENTRIES);
    assertNotNull(map.get(1));
    assertNull(map.get(2));
  }
}
