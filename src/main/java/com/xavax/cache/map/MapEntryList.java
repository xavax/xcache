//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.map;

/**
 * MapEntryList is the base class for map entry lists such as the least recently
 * used chain of cache map entries or the various map entry free lists.
 * 
 * @author alvitar@xavax.com
 */
public class MapEntryList {

  public MapEntryList() {
    this.head = new MapEntry();
  }

  /**
   * Insert a map entry at the beginning of a list.
   * 
   * @param entry the map entry to insert.
   */
  public void insert(MapEntry entry) {
    if ( entry != null ) {
      boolean run = true;
      do {
	if ( head.tryLock() ) {
	  MapEntry next = head.next();
	  if ( next == head || next.tryLock() ) {
	    entry.connect(next, head);
	    if ( next != head ) {
	      next.unlock();
	    }
	    run = false;
	  }
	  head.unlock();
	}
      } while ( run );
    }
  }

  /**
   * Append a map entry at the end of a list.
   * 
   * @param entry the map entry to append.
   */
  public void append(MapEntry entry) {
    if ( entry != null ) {
      boolean run = true;
      do {
	if ( head.tryLock() ) {
	  MapEntry previous = head.previous();
	  if ( previous == head || previous.tryLock() ) {
	    entry.connect(head, previous);
	    if ( previous != head ) {
	      previous.unlock();
	    }
	    run = false;
	  }
	  head.unlock();
	}
      } while ( run );
    }
  }

  /**
   * Remove a map entry from a list.
   * 
   * @param entry the entry to be removed.
   */
  public void remove(MapEntry entry) {
    if ( entry != null ) {
      MapEntry next = entry.next();
      MapEntry previous = entry.previous();
      if ( next != null && previous != null ) {
	boolean run = true;
	do {
	  if ( previous.tryLock() ) {
	    if ( entry.tryLock() ) {
	      if ( next == previous || next.tryLock() ) {
		entry.disconnect();
		if ( next != previous ) {
		  next.unlock();
		}
	      }
	      entry.unlock();
	    }
	    previous.unlock();
	  }
	} while ( run );
      }
    }
  }

  /**
   * Move an entry to the tail of the list.
   *
   * @param entry  the entry to move.
   */
  public void moveToTail(MapEntry entry) {
    remove(entry);
    append(entry);
  }

  private MapEntry head;
}
