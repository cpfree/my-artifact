package com.github.cpfniliu.tool.pixelpic;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@Getter
public class PixelPngSource {

    /**
     * 源类型: {文件地址, 网络地址, 二进制流, 剪贴板}
     */
    public enum SourceType {
        TYPE_FILE, TYPE_CONTENT, TYPE_CLIPBOARD
    }

    private SourceType sourceType;

    private byte[] content;

    private File file;

    public PixelPngSource(File file) {
        this.file = file;
        this.sourceType = SourceType.TYPE_FILE;
    }

    public void init() throws IOException {
        final long length = file.length();
        Validate.isTrue(length < Integer.MAX_VALUE, "文件过大");
        content = new byte[(int) length];
        // 写入文件
        try (FileInputStream in = new FileInputStream(file)) {
            final int read = in.read(content);
            Validate.isTrue(read == (int) length, "读取错误");
        }
    }

    public void checkWithThrow() throws IOException {
        Objects.requireNonNull(getContent(), "无内容");
        Objects.requireNonNull(getSourceType(), "无SourceType");
    }

    public byte[] getContent() throws IOException {
        if (content == null) {
            init();
        }
        return content;
    }

    public String getName() {
        return file.getName();
    }

}
