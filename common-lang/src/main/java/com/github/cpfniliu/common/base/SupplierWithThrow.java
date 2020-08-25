package com.github.cpfniliu.common.base;

/**
 * <b>Description : </b> 带有抛出异常的 Supplier 接口
 *
 * @author CPF
 * Date: 2020/6/29 15:29
 */
@FunctionalInterface
public interface SupplierWithThrow<T> {

    T get() throws Exception;
}
