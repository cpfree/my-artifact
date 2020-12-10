package com.github.cpfniliu.logadapter.logging;

/**
 * 日志异常,继承PersistenceException，没啥好说的，就是语义分类
 * 
 */
public class LogException extends RuntimeException {

  private static final long serialVersionUID = 1022924004852350942L;

  public LogException() {
    super();
  }

  public LogException(String message) {
    super(message);
  }

  public LogException(String message, Throwable cause) {
    super(message, cause);
  }

  public LogException(Throwable cause) {
    super(cause);
  }

}
