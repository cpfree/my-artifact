package com.github.cpfniliu.common.base;

import java.io.File;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/5/12 17:32
 */
@FunctionalInterface
public interface FileDisposer {

    void dispose(File file);

}
