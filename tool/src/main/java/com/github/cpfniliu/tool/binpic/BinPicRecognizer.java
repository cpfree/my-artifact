package com.github.cpfniliu.tool.binpic;

import com.github.cpfniliu.common.util.common.ArrUtils;
import com.github.cpfniliu.common.util.io.IoUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description : </b> 解析二进制图片工具类
 *
 * @author CPF
 * Date: 2020/5/19 18:15
 */
@Slf4j
public class BinPicRecognizer {

    /**
     * 将路径指向的 binPic 转换为文件并存储到指定文件夹
     *
     * @param picPath binPic 图片路径
     * @param saveDirPath 解析后的文件存储路径
     */
    public static boolean convertBinPicToFile(String picPath, String saveDirPath) throws IOException {
        final BufferedImage image = BinPicUtils.load(picPath);
        final PixelReader resolver = BinPicRecognizer.resolver(image);
        boolean check = resolver.check();
        if (!check) {
            log.error("转换文件失败, MD5值不一样");
            return false;
        }
        // 确保存储的文件夹存在
        IoUtils.insureFileDirExist(new File(saveDirPath));
        // 写入文件
        try (FileOutputStream outputStream = new FileOutputStream(new File(saveDirPath + resolver.getBinPicHeader().getFileName()))){
            outputStream.write(resolver.fileContent);
        }
        return true;
    }

    /**
     * 将路径指向的 binPic 转换为文件并存储到 binPic 路径下的 outfile 文件夹
     *
     * @param picPath binPic 图片路径
     */
    public static boolean convertBinPicToFileFromSourcePath(String picPath) throws IOException {
        return convertBinPicToFile(picPath, new File(picPath).getParentFile().getPath() + File.separator + "outfile" + File.separator);
    }

    /**
     * @param image 待识别的图片
     * @return 识别后的识别器对象
     */
    public static PixelReader resolver(BufferedImage image) {
        BinPicRecognizer recognizer = new BinPicRecognizer();
        recognizer.setImage(image);
        recognizer.distinguish();
        recognizer.pixelReader.readFileInfo();
        return recognizer.getPixelReader();
    }

    /**
     * 图片
     */
    @Setter
    private BufferedImage image;

    /**
     * 左上方标记点
     */
    @Getter
    private Point leftTopPoint;
    /**
     * 右上方标记点
     */
    @Getter
    private Point rightTopPoint;
    /**
     * 左下方标记点
     */
    @Getter
    private Point leftBottomPoint;

    @Getter
    private PixelReader pixelReader;

    /**
     *
     */
    public static class PixelReader {
        @Getter
        private BufferedImage image;

        private int[] xArr;

        private int[] yArr;

        public PixelReader(BufferedImage image, int[] xArr, int[] yArr) {
            this.image = image;
            this.xArr = xArr;
            this.yArr = yArr;
            init();
        }

        /**
         * 读取像素位置(用于计算下次读取位置)
         */
        int no;

        @Getter
        private byte[] fileContent;

        private int[] byteModal;

        @Getter
        private int radix;

        private int powOf2;

        @Getter
        private BinPicHeader binPicHeader;

        @Getter
        private int contentLength;

        /**
         * 读取初始化数据
         */
        public void init() {
            no = 0;
            int[] powOf2Bin = readPixel(8);
            int[] oneTwo = readPixel(2);
            no -= 2;
            powOf2 = deCode(oneTwo, powOf2Bin, 1);
            radix = (int) Math.pow(2, powOf2);
            byteModal = readPixel(radix);
        }

        /**
         * 将一段整形数据 按规则 解析成一个整数
         *
         * @param byteModal 解析 byte 类型
         * @param vals int 值
         * @param bit 基数位
         * @return 解析后的整数
         */
        public int deCode(int[] byteModal, int[] vals, int bit) {
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

        public byte[] deCodeToByte(int[] byteModal, int[] vals) {
            int max = byteModal.length;
            byte[] bytes = new byte[vals.length];
            for (int i = 0; i < vals.length; i++) {
                int v = ArrUtils.indexOf(byteModal, vals[i]);
                if (v < 0 || v >= max) {
                    throw new RuntimeException();
                }
                bytes[i] = (byte) v;
            }
            return BinPicUtils.deCodeToByte(powOf2, bytes);
        }

        /**
         * 检查文件MD5值
         */
        public boolean check() {
            String md5Hex = BinPicUtils.encrypt2ToMd5(fileContent);
            String md5 = binPicHeader.getMd5();
            log.info("像素head信息MD5值: {}", md5);
            log.info("文件解析内容MD5值: {}", md5);
            return md5.equalsIgnoreCase(md5Hex);
        }

        /**
         * 读取文件信息
         */
        public void readFileInfo() {
            // 一行有多少像素
            int[] rowPixelCnt = readPixel(4 * 8 / powOf2);
            int rowPxNumLength = deCode(byteModal, rowPixelCnt, powOf2);
            Validate.isTrue(rowPxNumLength == xArr.length, String.format("rowPxNumLength : %S != xArr.length: %s", rowPxNumLength, xArr.length));
            // 文件信息长度
            int[] fileInfoLength = readPixel(4 * 8 / powOf2);
            contentLength = deCode(byteModal, fileInfoLength, powOf2);
            // 读取文件信息
            byte[] content = readByte(contentLength);
            // 文件头
            final String json = new String(content);
            log.info("文件头信息: {}", json);
            binPicHeader = BinPicHeader.fromJson(json);
            // 文件内容
            fileContent = readByte(binPicHeader.getFileContentLength());
        }

        private byte[] readByte(long byteLength) {
            int[] ints = readPixel((int) (byteLength * 8 / powOf2));
            return deCodeToByte(byteModal, ints);
        }

        /**
         * @param number 读取 pixel 数目
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

    /**
     * 识别
     */
    @SuppressWarnings({"java:S3776", "java:S1199", "java:S3518"})
    public void distinguish(){
        // 图片高度
        int height = image.getHeight();
        // 图片宽度
        int width = image.getWidth();

        {
            // 寻找四边
            // 斜线切入
            int min = Math.min(height, width);
            int r = 0;
            while (r < min) {
                int rgb = image.getRGB(r, r);
                if (isBorderVal(rgb)) {
                    break;
                }
                r++;
            }
            // 判断是否是横轴还是中轴
            if (isBorderVal(image.getRGB(r - 1, r))) {
                int x = r - 2;
                while (isBorderVal(image.getRGB(x, r))) x--;
                leftTopPoint = new Point(++ x, r);
            } else if (isBorderVal(image.getRGB(r, r-1))) {
                int y = r - 2;
                while (isBorderVal(image.getRGB(r, y))) y--;
                leftTopPoint = new Point(r, ++ y);
            } else {
                leftTopPoint = new Point(r, r);
            }
        }

        // 验证
        if (isBorderVal(image.getRGB(leftTopPoint.x - 1, leftTopPoint.y)) || isBorderVal(image.getRGB(leftTopPoint.x, leftTopPoint.y - 1))) {
            throw new RuntimeException("像素位置验证失败");
        }
        // 左上方标记点应为黑色
        Validate.isTrue(isBlack(image.getRGB(leftTopPoint.x, leftTopPoint.y)), "isNotBlack");

        // 找到xy 有效像素列表
        {
            boolean isBlack = true;
            int cnt = 0;
            int sum = 0;
            List<Integer> xList = new ArrayList<>();
            for (int x = leftTopPoint.x, y = leftTopPoint.y; x < width; x++) {
                int rgb = image.getRGB(x, y);
                if (!isBorderVal(rgb)) {
                    xList.add(sum / cnt);
                    rightTopPoint = new Point(x - 1, y);
                    break;
                }
                if ((isBlack && isBlack(rgb)) || (!isBlack && isWhite(rgb))) {
                    cnt ++;
                    sum += x;
                } else {
                    xList.add(sum / cnt);
                    cnt = 1;
                    sum = x;
                    isBlack = !isBlack;
                }
            }
            xList.remove(xList.size() - 1);
            xList.remove(0);
            int[] xArr = xList.stream().mapToInt(it -> it).toArray();
            xList.clear();
            isBlack = true;
            cnt = 0;
            sum = 0;
            for (int x = leftTopPoint.x, y = leftTopPoint.y; y < height; y++) {
                int rgb = image.getRGB(x, y);
                if (!isBorderVal(rgb)) {
                    xList.add(sum / cnt);
                    leftBottomPoint = new Point(x, y - 1);
                    break;
                }
                if ((isBlack && isBlack(rgb)) || (!isBlack && isWhite(rgb))) {
                    cnt ++;
                    sum += y;
                } else {
                    xList.add(sum / cnt);
                    cnt = 1;
                    sum = y;
                    isBlack = !isBlack;
                }
            }
            xList.remove(xList.size() - 1);
            xList.remove(0);
            int[] yArr = xList.stream().mapToInt(it -> it).toArray();

            pixelReader = new PixelReader(image, xArr, yArr);
        }
    }

    public boolean checkMd5() {
        return pixelReader.check();
    }

    /**
     * 判断定位区像素是否是白色或黑色
     *
     * @param rgb rgb 值
     */
    public static boolean isBorderVal(int rgb) {
        return isBlack(rgb) || isWhite(rgb);
    }

    /**
     * 判断定位区像素是否是黑色
     *
     * @param rgb rgb 值
     */
    public static boolean isBlack(int rgb) {
        int n = 0;
        n += (rgb >>> 16 & 0xFF);
        n += (rgb >>> 8 & 0xFF);
        n += (rgb & 0xFF);
        return n < 100;
    }

    /**
     * 判断定位区像素是否是白色
     *
     * @param rgb rgb 值
     */
    public static boolean isWhite(int rgb) {
        int n = 0;
        n += (rgb >>> 16 & 0xFF);
        n += (rgb >>> 8 & 0xFF);
        n += (rgb & 0xFF);
        return n > 665;
    }

}
