package com.github.cpfniliu.common.base;

/**
 * <b>Description : </b> 带有抛出异常的 Runnable 接口
 *
 * @author CPF
 * Date: 2020/6/29 15:38
 */
@FunctionalInterface
public interface RunnableWithThrow {

    void run() throws Exception;

}
