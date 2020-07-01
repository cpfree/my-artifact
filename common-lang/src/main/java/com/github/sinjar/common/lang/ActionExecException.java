package com.github.sinjar.common.lang;

/**
 * <b>Description : </b> 执行 action 发生异常
 *
 * @author CPF
 * Date: 2020/6/23 14:34
 */
public class ActionExecException extends RuntimeException {

    public ActionExecException() {
        super();
    }

    public ActionExecException(String actionName) {
        super("发生异常: " + actionName);
    }

    public ActionExecException(String actionName, Throwable cause) {
        super("发生异常: " + actionName, cause);
    }

    public ActionExecException(Throwable cause) {
        super(cause);
    }
}
