package com.xavax.cache;

public class CacheBuilderException extends Exception {
  public CacheBuilderException(String message, Throwable cause) {
    super(message, cause);
  }

  public CacheBuilderException(String message) {
    super(message);
  }

  public CacheBuilderException() {
    super(DEFAULT_MESSAGE);
  }

  private final static String DEFAULT_MESSAGE =
      "Must specify an adapter class before setting attributes.";
  private final static long serialVersionUID = 1L;
}
