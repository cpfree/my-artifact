package cn.cpf.test.man;

import com.github.cpfniliu.tool.pixelpic.PixelPicUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 像素点阵图片解析器(用于命令行调用)
 */
@SuppressWarnings("java:S106")
public class ResolvePixelLattice {

    public static void main(String[] args) throws IOException {
//        if (args == null || args.length < 1) {
//            throw new RuntimeException("参数不能为空");
//        }

//        final String path = args[0];
        final String path = "D:\\Users\\CPF\\Desktop\\2020-10-30_182144.png";
        String savePath;
        if (args.length >= 2) {
            savePath = args[1];
        }  else {
            savePath = getFileDir(path) + "out" + File.separator;
        }
        BufferedImage image = PixelPicUtils.load(path);
        ClipboardPixelAnalysis.convertBinPicToFile(image, savePath);
    }

    /**
     * 将路径指向的 PixelPic 转换为文件并存储到 PixelPic 路径下的 outfile 文件夹
     *
     * @param picPath PixelPic 图片路径
     */
    public static String getFileDir(String picPath) {
        return new File(picPath).getParentFile().getPath() + File.separator;
    }

}
