package com.xavax.cache.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TimeMetricTest {
  private final static long START_TIME = 1000;
  private final static long STOP_TIME = 1100;

  private TimeMetric metric;

  @BeforeMethod
  public void setup() {
    metric = new TimeMetric();
  }

  @Test
  public void testResult() {
    TimeMetric.Result result = metric.result();
    assertNotNull(result);
  }

  @Test
  public void testAddTransaction() {
    metric.addTransaction(START_TIME, STOP_TIME);
    metric.addTransaction(START_TIME, STOP_TIME + 1);
    metric.addTransaction(START_TIME, STOP_TIME + 2);
    metric.addTransaction(START_TIME, STOP_TIME + 3);
    metric.addTransaction(START_TIME, STOP_TIME + 4);
    TimeMetric.Result result = metric.result();
    assertEquals(result.count(), 5);
  }

  @Test
  public void testToString() {
    metric.addTransaction(START_TIME, STOP_TIME);
    TimeMetric.Result result = metric.result();
    result = metric.result();
    assertEquals(result.toString(), "(1, 100, 100, 100.0, 0.0)");
    metric.addTransaction(START_TIME, STOP_TIME + 1);
    metric.addTransaction(START_TIME, STOP_TIME + 2);
    metric.addTransaction(START_TIME, STOP_TIME + 3);
    metric.addTransaction(START_TIME, STOP_TIME + 4);
    result = metric.result();
    assertEquals(result.toString(), "(5, 100, 104, 102.0, 1.4142135623730951)");
  }
}
