package com.github.sinjar.common.util.io;

import com.github.sinjar.common.FileDisposer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/5/12 17:28
 */
@Slf4j
public class FileSystemUtils {

    private FileSystemUtils(){}

    public static void fileDisposeFromDir(File file, FileDisposer fileDisposer, FileFilter fileFilter) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()){
            // 过滤
            if (fileFilter != null && !fileFilter.accept(file)) {
                return;
            }
            fileDisposer.dispose(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File item : files) {
                    fileDisposeFromDir(item, fileDisposer, fileFilter);
                }
            }
        } else {
            log.debug("非文件或文件夹, 跳过处理! filePath: {}", file.getPath());
        }
    }

}
