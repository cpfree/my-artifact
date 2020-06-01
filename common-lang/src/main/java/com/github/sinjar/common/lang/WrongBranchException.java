package com.github.sinjar.common.lang;

/**
 * <b>Description : </b> 错误分支异常
 *
 * @author CPF
 * Date: 2020/3/24 16:20
 */
public class WrongBranchException extends RuntimeException {

    public WrongBranchException() {
        super();
    }

    public WrongBranchException(String message) {
        super(message);
    }

    public WrongBranchException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongBranchException(Throwable cause) {
        super(cause);
    }
}
