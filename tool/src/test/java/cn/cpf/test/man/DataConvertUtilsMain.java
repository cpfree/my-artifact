package cn.cpf.test.man;

import com.github.cpfniliu.common.util.io.IoUtils;
import com.github.cpfniliu.tool.util.DataConvertUtils;

import java.io.File;
import java.io.IOException;

public class DataConvertUtilsMain {
    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 1) {
            throw new RuntimeException("参数不能为空");
        }
        final String path = args[0];
        String savePath;
        if (args.length >= 2) {
            savePath = args[1];
        }  else {
            savePath = getFileDir(path);
        }
        final String s = DataConvertUtils.fileToBase64(new File(path));
        IoUtils.writeFile(savePath, s.getBytes());
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
