package com.github.cpfniliu.tool.binpic;

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
public class BinPicHeader implements Serializable {

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

    public static BinPicHeader fromJson(String json) {
        return new Gson().fromJson(json, BinPicHeader.class);
    }

    public String toJson() {
        final String format = "{\"version\":%s,\"versionTime\":%s,\"enTime\":%s,\"fileName\":\"%s\",\"fileContentLength\":%s,\"md5\":\"%s\",\"type\":\"%s\"}";
        return String.format(format, version, versionTime, enTime, fileName, fileContentLength, md5, type);
    }

    @SuppressWarnings("java:S106")
    public static void main(String[] args) {
        BinPicHeader header = new BinPicHeader();
        header.setFileName("测试名称.fjk");
        header.setFileContentLength(23423420L);
        header.setMd5("432423-fe-asu-de[e");
        header.setType(TYPE_FILE);
        final String s = header.toJson();
        final BinPicHeader header1 = BinPicHeader.fromJson(s);
        System.out.println(header1);
    }

}