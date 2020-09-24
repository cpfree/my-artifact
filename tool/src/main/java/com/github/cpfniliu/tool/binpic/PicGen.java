package com.github.cpfniliu.tool.binpic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PicGen {

    BufferedImage genePixelImage(Object source, Object pixelConfig) {
        return null;
    }

    class PixelConfig {

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

}
