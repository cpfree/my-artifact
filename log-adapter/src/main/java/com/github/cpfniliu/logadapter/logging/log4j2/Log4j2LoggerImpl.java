/*
 *    Copyright 2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.cpfniliu.logadapter.logging.log4j2;

import com.github.cpfniliu.logadapter.logging.Log;
import com.github.cpfniliu.logadapter.logging.LogFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * log4j2的logger，和slf4j一样，也是代理模式，底下的Log4j2AbstractLoggerImpl或Log4j2Impl才是真正的log
 *
 */
public class Log4j2LoggerImpl implements Log {
  
  private static Marker MARKER = MarkerManager.getMarker(LogFactory.MARKER);

  private Logger log;

  public Log4j2LoggerImpl(Logger logger) {
    log = logger;
  }

  @Override
  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  @Override
  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  @Override
  public void error(String s, Throwable e) {
    log.error(MARKER, s, e);
  }

  @Override
  public void error(String s) {
    log.error(MARKER, s);
  }

  @Override
  public void debug(String s) {
    log.debug(MARKER, s);
  }

  @Override
  public void trace(String s) {
    log.trace(MARKER, s);
  }

  @Override
  public void warn(String s) {
    log.warn(MARKER, s);
  }

}