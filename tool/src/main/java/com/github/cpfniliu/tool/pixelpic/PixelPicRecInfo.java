package com.github.cpfniliu.tool.pixelpic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PixelPicRecInfo {

    private PixelPicHeader pixelPicHeader;

    private int[] byteModal;
    /**
     * x 像素区域列表
     */
    private int[] xArr;

    /**
     * y 像素区域列表
     */
    private int[] yArr;

    private byte[] fileContent;

    private int pixelTypeCnt;

    private int bitCnt;

    private int contentLength;

    /**
     * 检查文件MD5值
     */
    public boolean check() {
        String md5Hex = PixelPicUtils.encrypt2ToMd5(fileContent);
        String md5 = pixelPicHeader.getMd5();
        log.info("像素head信息MD5值: {}", md5);
        log.info("文件解析内容MD5值: {}", md5);
        return md5.equalsIgnoreCase(md5Hex);
    }

    @Override
    public String toString() {
        return "PixelPicRecInfo{" +
                "pixelPicHeader=" + pixelPicHeader +
                ", pixelTypeCnt=" + pixelTypeCnt +
                ", bitCnt=" + bitCnt +
                ", contentLength=" + contentLength +
                '}';
    }
}
