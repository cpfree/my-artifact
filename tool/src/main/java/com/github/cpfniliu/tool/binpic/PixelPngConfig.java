package com.github.cpfniliu.tool.binpic;

import java.awt.*;

/**
 * <b>Description : </b> 二进制图片配置类
 *
 * @author CPF
 * Date: 2020/5/19 18:15
 */
public class PixelPngConfig {

    /**
     * 代表像素图片 8 * 4
     */
    private int type = 0;
    /**
     * 版本号: 8 * 4
     */
    private int version = 1;
    /**
     * 版本号: 8 * 4
     */
    private long versionTime = 1589939253407L;
    /**
     * 图片宽度
     */
    private int imageWidth;
    /**
     * 图片高度
     */
    private int imageHeight;
    /**
     * 绘制区域
     */
    private Rectangle area;
    /**
     * 像素点阵边长
     */
    private int pixelSideLength;
    /**
     * 映射图片
     */
    private Color[] mappingColor;

}
