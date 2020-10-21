package com.github.cpfniliu.tool.pixelpic;

import com.google.gson.Gson;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>Description : </b> 头实体信息
 *
 * @author CPF
 * Date: 2020/5/21 14:17
 */
@Data
public class PixelPicHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_FILE = "TYPE_FILE";
    public static final String TYPE_CONTENT = "TYPE_CONTENT";
    public static final String TYPE_CLIPBOARD = "TYPE_CLIPBOARD";

    /**
     * 版本号: 8 * 4
     */
    private int version = 1;
    /**
     * 版本号: 8 * 4
     */
    private long versionTime = 1589939253407L;
    /**
     * 加压时间
     */
    private long enTime = new Date().getTime();
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件内容长度  8 * 8
     */
    private long fileContentLength;
    /**
     * MD5值
     */
    private String md5;

    /**
     * 标记类型
     */
    private String type;

    public static PixelPicHeader fromJson(String json) {
        return new Gson().fromJson(json, PixelPicHeader.class);
    }

    public String toJson() {
        final String format = "{\"version\":%s,\"versionTime\":%s,\"enTime\":%s,\"fileName\":\"%s\",\"fileContentLength\":%s,\"md5\":\"%s\",\"type\":\"%s\"}";
        return String.format(format, version, versionTime, enTime, fileName, fileContentLength, md5, type);
    }

}