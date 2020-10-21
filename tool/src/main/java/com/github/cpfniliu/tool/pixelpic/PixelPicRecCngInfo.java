package com.github.cpfniliu.tool.pixelpic;

import lombok.Data;

import java.awt.*;

/**
 * 像素图片识别信息
 */
@Data
public class PixelPicRecCngInfo {
    /**
     * 左上方标记点
     */
    private Point leftTopPoint;
    /**
     * 右上方标记点
     */
    private Point rightTopPoint;
    /**
     * 左下方标记点
     */
    private Point leftBottomPoint;
    /**
     * 左下方标记点
     */
    private Point rightBottomPoint;

    private int[] xArr;

    private int[] yArr;

}
