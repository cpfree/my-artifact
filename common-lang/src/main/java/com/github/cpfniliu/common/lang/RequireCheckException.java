package com.github.cpfniliu.common.lang;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/3/24 16:31
 */
public class RequireCheckException extends RuntimeException {

    public RequireCheckException() {
        super();
    }

    public RequireCheckException(String message) {
        super(message);
    }

    public RequireCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequireCheckException(Throwable cause) {
        super(cause);
    }
}
