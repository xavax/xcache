//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache;

/**
 * Positional is an interface for resources that have a method
 * indicating their position within an array.
 *
 * @author alvitar@xavax.com
 */
public interface Positional {

  /**
   * Set the slot number of this resource.
   *
   * @param position  the position of this resource.
   */
  public void position(int position);

  /**
   * Return the position of this resource.
   *
   * @return the position of this resource.
   */
  public int position();

}