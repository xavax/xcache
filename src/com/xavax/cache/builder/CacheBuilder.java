//
// Copyright 2013 by Xavax, Inc. All Rights Reserved.
// Use of this software is allowed under the Xavax Open Software License.
// http://www.xavax.com/xosl.html
//
package com.xavax.cache.builder;

import java.util.List;

import com.xavax.base.XObject;
import com.xavax.cache.CacheManager;
import com.xavax.cache.WritePolicy;
import com.xavax.util.CollectionFactory;

public class CacheBuilder<K,V> extends XObject {

  protected CacheBuilder() {
    adapterBuilders = CollectionFactory.arrayList();
  }

  protected CacheBuilder<K,V> withWritePolicy(WritePolicy writePolicy) {
    this.writePolicy = writePolicy;
    return this;
  }

  protected CacheBuilder<K,V> withAdapter(CacheAdapterBuilder<K,V> builder) throws CacheBuilderException {
    if ( builder == null ) {
      throw new CacheBuilderException("null cache adapter builder");
    }
    this.adapterBuilders.add(builder);
    return this;
  }

  public CacheBuilder<K,V> withManager(Class<? extends CacheManager<K,V>> manager)
      throws CacheBuilderException {
    if ( manager == null ) {
      throw new CacheBuilderException("null cache manager class");
    }
    this.managerClass = manager;
    return this;
  }

  public CacheManager<K,V> build() {
    final String method = "build";
    CacheManager<K,V> manager = null;
    try {
      manager = this.managerClass.newInstance();
    }
    catch ( Exception e ) {
      error(method, e, "exception while creating {}", this.managerClass.getName());
    }
    return manager;
  }

  WritePolicy writePolicy;
  Class<? extends CacheManager<K,V>> managerClass;
  List<CacheAdapterBuilder<K,V>> adapterBuilders;
}
