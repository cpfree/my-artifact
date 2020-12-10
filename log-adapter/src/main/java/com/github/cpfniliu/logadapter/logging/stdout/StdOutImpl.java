package com.github.cpfniliu.logadapter.logging.stdout;

import com.github.cpfniliu.logadapter.logging.Log;

/**
 * 打印到控制台的Log
 */
@SuppressWarnings("java:S106")
public class StdOutImpl implements Log {

  public StdOutImpl(String clazz) {
    // Do Nothing
  }

  @Override
  public boolean isDebugEnabled() {
    return true;
  }

  @Override
  public boolean isTraceEnabled() {
    return true;
  }

  @Override
  public void error(String s, Throwable e) {
    System.err.println(s);
    e.printStackTrace(System.err);
  }

  @Override
  public void error(String s) {
    System.err.println(s);
  }

  @Override
  public void debug(String s) {
    System.out.println(s);
  }

  @Override
  public void trace(String s) {
    System.out.println(s);
  }

  @Override
  public void warn(String s) {
    System.out.println(s);
  }
}
