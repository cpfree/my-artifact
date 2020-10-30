package cn.cpf.test.man;

import com.github.cpfniliu.common.util.io.IoUtils;
import com.github.cpfniliu.tool.pixelpic.PixelPicRecInfo;
import com.github.cpfniliu.tool.pixelpic.PixelPicRecognizer;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 将粘贴板中的图片转换为文件(用于命令行调用)
 */
public class ClipboardPixelAnalysis {

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        final Image imageFromClipboard = getImageFromClipboard();
        if (imageFromClipboard == null) {
            System.out.println("将粘贴板中未发现图片");
            return;
        }
        String save = "E:\\res\\FMBS_DEV_BANK\\inner\\clipout\\";
        BufferedImage image = (BufferedImage) imageFromClipboard;
        convertBinPicToFile(image, save);
    }

    /**
     * 将路径指向的 binPic 转换为文件并存储到指定文件夹
     *
     * @param image binPic 图片
     * @param saveDirPath 解析后的文件存储路径
     */
    @SuppressWarnings("java:S106")
    public static boolean convertBinPicToFile(BufferedImage image, String saveDirPath) throws IOException {
        // 确保存储的文件夹存在
        final PixelPicRecInfo picRecInfo = PixelPicRecognizer.resolver(image);
        if (picRecInfo == null) {
            System.out.println("未识别出像素图片区域");
            return false;
        }
        System.out.println("picRecInfo ==> \n" + picRecInfo);
        boolean check = picRecInfo.check();
        if (!check) {
            System.out.println("转换文件失败, MD5值不一样");
            return false;
        }
        if (!(saveDirPath.endsWith("\\") && saveDirPath.endsWith("/"))) {
            saveDirPath += File.separator;
        }
        System.out.println("转换文件成功!");
        IoUtils.insureFileDirExist(new File(saveDirPath));
        // 写入文件
        System.out.println("准备写入文件");
        try (FileOutputStream outputStream = new FileOutputStream(new File(saveDirPath + picRecInfo.getPixelPicHeader().getFileName()))){
            outputStream.write(picRecInfo.getFileContent());
        }
        System.out.println("写入文件成功");
        return true;
    }



    /**
     *3. 从剪切板获得图片。
     */
    public static Image getImageFromClipboard() throws IOException, UnsupportedFlavorException {
        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable cc = sysc.getContents(null);
        if (cc == null)
            return null;
        else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);
        return null;
    }

}
