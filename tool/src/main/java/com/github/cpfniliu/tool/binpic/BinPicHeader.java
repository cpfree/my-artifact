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

    public static BinPicHeader fromJson(String json) {
        return new Gson().fromJson(json, BinPicHeader.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}