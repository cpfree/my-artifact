package com.github.sinjar.common.base;


/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/6/29 15:29
 */
@FunctionalInterface
public interface SupplierWithThrow<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Exception;
}
