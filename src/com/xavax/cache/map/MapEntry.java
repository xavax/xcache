//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.map;

import java.util.concurrent.locks.ReentrantLock;

/**
 * MapEntry is the base class for map entries that are kept
 * in a linked list.
 *
 * @author alvitar@xavax.com
 */
@SuppressWarnings("serial")
public class MapEntry extends ReentrantLock {

  /**
   * Construct a MapEntry.
   */
  public MapEntry() {
    this.next = null;
    this.previous = null;
  }

  /**
   * Connect this map entry.
   *
   * @param next      the next map entry.
   * @param previous  the previous map entry.
   */
  public void connect(MapEntry next, MapEntry previous) {
    this.next = next;
    this.previous = previous;
    next.previous = this;
    previous.next = this;
  }

  /**
   * Disconnect this map entry.
   */
  public void disconnect() {
    if ( previous != null ) {
      previous.next = next;
    }
    if ( next != null ) {
      next.previous = previous;
    }
  }

  /**
   * Set the next map entry.
   *
   * @param next  the next map entry.
   */
  public void next(MapEntry next) {
    this.next = next;
  }

  /**
   * Return the next map entry.
   *
   * @return the next map entry.
   */
  public MapEntry next() {
    return this.next;
  }

  /**
   * Set the previous map entry.
   *
   * @param previous  the previous map entry.
   */
  public void previous(MapEntry previous) {
    this.previous = previous;
  }

  /**
   * Return the previous map entry.
   *
   * @return the previous map entry.
   */
  public MapEntry previous() {
    return this.previous;
  }

  private MapEntry next;
  private MapEntry previous;
}
