package cn.cpf.test.keypress.man;

import com.github.cpfniliu.tool.binpic.BinPicRecognizer;

import java.io.IOException;

/**
 * 像素点阵图片解析器
 */
public class ResolvePixelLattice {

    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 1) {
            throw new RuntimeException("参数不能为空");
        }
        final String path = args[0];
        if (args.length >= 2) {
            final String savePath = args[1];
            BinPicRecognizer.convertBinPicToFile(path, savePath);
        }  else {
            BinPicRecognizer.convertBinPicToFileFromSourcePath(path);
        }
    }
}
