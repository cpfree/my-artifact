package com.github.cpfniliu.tool.pixelpic;

import com.github.cpfniliu.common.util.common.ArrUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import java.awt.image.BufferedImage;

/**
 * 像素读取
 */
@Slf4j
class PixelPngReader {
    @Getter
    private BufferedImage image;

    /**
     * x 像素区域列表
     */
    private int[] xArr;

    /**
     * y 像素区域列表
     */
    private int[] yArr;

    /**
     * 读取像素位置(用于计算下次读取位置)
     */
    private int no;

    @Getter
    private byte[] fileContent;

    private int[] byteModal;

    @Getter
    private int pixelTypeCnt;

    private int bitCnt;

    @Getter
    private PixelPicHeader pixelPicHeader;

    @Getter
    private int contentLength;

    public PixelPngReader(BufferedImage image, int[] xArr, int[] yArr) {
        this.image = image;
        this.xArr = xArr;
        this.yArr = yArr;
        init();
    }

    /**
     * 读取初始化数据
     */
    public void init() {
        no = 0;
        int[] powOf2Bin = readPixel(8);
        int[] oneTwo = readPixel(2);
        no -= 2;
        bitCnt = deCode(oneTwo, powOf2Bin, 1);
        pixelTypeCnt = (int) Math.pow(2, bitCnt);
        byteModal = readPixel(pixelTypeCnt);
    }

    /**
     * 将一段整形数据 按规则 解析成一个整数
     * TODO 放宽对比规则
     *
     * @param byteModal 解析 byte 类型
     * @param vals int 值
     * @param bit 基数位
     * @return 解析后的整数
     */
    public static int deCode(int[] byteModal, int[] vals, int bit) {
        int val = 0;
        for (int i : vals) {
            int i1 = ArrUtils.indexOf(byteModal, i);
            if (i1 < 0) {
                throw new RuntimeException("像素失真! 在byteModal中未发现xi相关数据;");
            }
            val = val << bit | i1;
        }
        return val;
    }

    /**
     * @param vals
     * @return
     */
    private byte[] deCodeToByte(int[] vals) {
        int max = byteModal.length;
        byte[] bytes = new byte[vals.length];
        for (int i = 0; i < vals.length; i++) {
            int v = ArrUtils.indexOf(byteModal, vals[i]);
            if (v < 0 || v >= max) {
                throw new RuntimeException();
            }
            bytes[i] = (byte) v;
        }
        return BinPicUtils.deCodeToByte(bitCnt, bytes);
    }

    /**
     * 检查文件MD5值
     */
    public boolean check() {
        String md5Hex = BinPicUtils.encrypt2ToMd5(fileContent);
        String md5 = pixelPicHeader.getMd5();
        log.info("像素head信息MD5值: {}", md5);
        log.info("文件解析内容MD5值: {}", md5);
        return md5.equalsIgnoreCase(md5Hex);
    }

    /**
     * 读取文件信息
     */
    public void readFileInfo() {
        // 1 byte所占bit数 和 一个像素所占bit之比
        int bi = 8 / bitCnt;
        // 一行有多少像素
        int[] rowPixelCnt = readPixel(4 * bi);
        int rowPxNumLength = deCode(byteModal, rowPixelCnt, bitCnt);
        Validate.isTrue(rowPxNumLength == xArr.length, String.format("rowPxNumLength : %S != xArr.length: %s", rowPxNumLength, xArr.length));
        // 文件信息长度
        int[] fileInfoLength = readPixel(4 * bi);
        contentLength = deCode(byteModal, fileInfoLength, bitCnt);
        // 读取文件信息
        int[] intSerial = readPixel(contentLength * bi);
        byte[] content = deCodeToByte(intSerial);
        // 文件头
        final String json = new String(content);
        log.info("文件头信息: {}", json);
        pixelPicHeader = PixelPicHeader.fromJson(json);
        // 文件内容
        intSerial = readPixel((int) (pixelPicHeader.getFileContentLength() * bi));
        fileContent = deCodeToByte(intSerial);
    }

    /**
     * @param number 读取像素图片内容区中指定数目的 pixel, 并返回
     * @return 读取的像素值
     */
    @SuppressWarnings({"java:S1994", "java:S127"})
    public int[] readPixel(int number) {
        int xLength = xArr.length;
        int yLength = yArr.length;
        int[] arr = new int[number];
        int i = 0;
        for (int y = no / xLength, x = no % xLength; y < yLength && i < number; y++){
            for (; x < xLength && i < number; x++) {
                int rgb = image.getRGB(xArr[x], yArr[y]);
                arr[i] = rgb;
                i ++;
            }
            x = 0;
        }
        no += number;
        return arr;
    }
}
