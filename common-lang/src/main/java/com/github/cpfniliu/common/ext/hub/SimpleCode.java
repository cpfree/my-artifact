package com.github.cpfniliu.common.ext.hub;

import com.github.cpfniliu.common.base.RunnableWithThrow;
import com.github.cpfniliu.common.base.SupplierWithThrow;
import com.github.cpfniliu.common.lang.ActionExecException;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Description : </b> 简化代码类
 * <p>
 * runtimeException开头, 原来需要catch的运行时转换为runtimeException抛出
 * ignoreException开头, 忽略发生的异常, 仅仅打印日志.
 *
 * @author CPF
 * Date: 2020/6/24 18:00
 */
@Slf4j
public class SimpleCode {

    private SimpleCode() {
    }

    /**
     * 忽略运行的异常
     *
     * @param runnable 带有异常的运行接口
     * @param message  发生异常报错信息
     * @see SimpleCode#simpleException(RunnableWithThrow, java.lang.String, boolean)
     */
    public static void ignoreException(RunnableWithThrow runnable, String message) {
        simpleException(runnable, message, false);
    }

    /**
     * 忽略运行的异常
     *
     * @param runnable 带有异常的运行接口
     * @see SimpleCode#simpleException(RunnableWithThrow, java.lang.String, boolean)
     */
    public static void ignoreException(RunnableWithThrow runnable) {
        simpleException(runnable, null, false);
    }

    /**
     * 异常转换为 ActionExecException
     *
     * @param runnable 带有异常的运行接口
     * @param message  发生异常报错信息
     * @see SimpleCode#simpleException(RunnableWithThrow, java.lang.String, boolean)
     */
    public static void runtimeException(RunnableWithThrow runnable, String message) {
        simpleException(runnable, message, true);
    }

    /**
     * 异常转换为 ActionExecException
     *
     * @param runnable 带有异常的运行接口
     * @see SimpleCode#simpleException(RunnableWithThrow, java.lang.String, boolean)
     */
    public static void runtimeException(RunnableWithThrow runnable) {
        simpleException(runnable, null, true);
    }

    /**
     * 忽略运行的异常
     *
     * @param supplier 带有返回值和throw的函数接口
     * @see SimpleCode#simpleException(SupplierWithThrow, java.lang.Object, java.lang.String, boolean)
     */
    public static <T> T ignoreException(SupplierWithThrow<T> supplier) {
        return simpleException(supplier, null, null, false);
    }

    /**
     * 忽略运行的异常
     *
     * @param supplier     带有返回值和throw的函数接口
     * @param defaultValue supplier 发生错误后的默认返回值
     * @see SimpleCode#simpleException(SupplierWithThrow, java.lang.Object, java.lang.String, boolean)
     */
    public static <T> T ignoreException(SupplierWithThrow<T> supplier, T defaultValue) {
        return simpleException(supplier, defaultValue, null, false);
    }

    /**
     * 忽略运行的异常
     *
     * @param supplier     带有返回值和throw的函数接口
     * @param defaultValue supplier 发生错误后的默认返回值
     * @see SimpleCode#simpleException(SupplierWithThrow, java.lang.Object, java.lang.String, boolean)
     */
    public static <T> T ignoreException(SupplierWithThrow<T> supplier, T defaultValue, String message) {
        return simpleException(supplier, defaultValue, message, false);
    }

    /**
     * 忽略运行的异常
     *
     * @param supplier 带有返回值和throw的函数接口
     * @see SimpleCode#simpleException(SupplierWithThrow, java.lang.Object, java.lang.String, boolean)
     */
    public static <T> T runtimeException(SupplierWithThrow<T> supplier) {
        return simpleException(supplier, null, null, true);
    }

    /**
     * 原来需要 catch 的运行时转换为 runtimeException 抛出
     *
     * @param supplier 带有返回值和throw的函数接口
     * @param message  supplier 发生错误后的信息
     * @see SimpleCode#simpleException(SupplierWithThrow, java.lang.Object, java.lang.String, boolean)
     */
    public static <T> T runtimeException(SupplierWithThrow<T> supplier, String message) {
        return simpleException(supplier, null, message, true);
    }

    /**
     * 忽略运行的异常
     *
     * @param runnable       指定的函数接口
     * @param message        runnable 发生错误后的信息
     * @param throwException true: runnable 发生错误后抛出运行时异常, false: 仅仅打印日志
     */
    public static void simpleException(RunnableWithThrow runnable, String message, boolean throwException) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (message == null) {
                message = "simpleException";
            }
            if (throwException) {
                throw new ActionExecException(message, e);
            } else {
                log.error(message, e);
            }
        }
    }

    /**
     * 忽略运行的异常
     *
     * @param supplier       带有返回值和throw的函数接口
     * @param defaultValue   throwException为true时, supplier 发生错误后的默认返回值
     * @param message        supplier 发生错误后的信息
     * @param throwException true: supplier 发生错误后抛出运行时异常, false: 仅仅打印日志
     * @param <T>            返回值类型
     * @return supplier 执行成功: supplier的返回值, supplier 执行失败: 返回 defaultValue
     */
    public static <T> T simpleException(SupplierWithThrow<T> supplier, T defaultValue, String message, boolean throwException) {
        return simpleException(supplier, defaultValue, message, true, throwException);
    }

    /**
     * 忽略运行的异常
     *
     * @param supplier       带有返回值和throw的函数接口
     * @param defaultValue   throwException为true时, supplier 发生错误后的默认返回值
     * @param message        supplier 发生错误后的信息
     * @param logException   输出异常
     * @param throwException true: supplier 发生错误后抛出运行时异常, false: 仅仅打印日志
     * @param <T>            返回值类型
     * @return supplier 执行成功: supplier的返回值, supplier 执行失败: 返回 defaultValue
     */
    public static <T> T simpleException(SupplierWithThrow<T> supplier, T defaultValue, String message, boolean logException, boolean throwException) {
        try {
            return supplier.get();
        } catch (Exception e) {
            if (throwException) {
                throw new ActionExecException(message, e);
            }
            if (logException) {
                log.warn(message, e);
            } else {
                log.warn(message);
            }
        }
        return defaultValue;
    }

}
