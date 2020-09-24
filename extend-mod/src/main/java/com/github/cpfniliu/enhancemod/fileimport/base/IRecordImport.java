package com.github.cpfniliu.enhancemod.fileimport.base;

import java.util.List;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/7/30 10:04
 */
@FunctionalInterface
public interface IRecordImport<T> {

    /**
     * 批量导入
     */
    int batchImport(List<T> quoteList);
}
