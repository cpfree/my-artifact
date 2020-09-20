package com.github.cpfniliu.common.lang;

/**
 * <b>Description : </b> 用于通用异常检查报错抛出
 *
 * @author CPF
 * Date: 2020/3/24 16:31
 */
public class CheckException extends RuntimeException {

    public CheckException() {
        super();
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }
}
