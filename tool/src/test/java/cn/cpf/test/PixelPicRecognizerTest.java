package cn.cpf.test;
import java.awt.image.BufferedImage;
import java.io.File;

import com.github.cpfniliu.tool.pixelpic.*;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.IOException;

public class PixelPicRecognizerTest {

    final String path = "D:\\Users\\CPF\\Desktop\\2020-10-30_182144";

    @Test
    public void testGne() throws IOException {
        PixelPicGeneConfig pixelPicGeneConfig = new PixelPicGeneConfig();
        pixelPicGeneConfig.setMargin(12, 10, 20, 14);
        pixelPicGeneConfig.setRowPixelCnt(850);
        pixelPicGeneConfig.setPixelSideWidth(1);
        pixelPicGeneConfig.setPixelSideHeight(2);
        pixelPicGeneConfig.setMappingColor(PixelPicUtils.getPxType(8));
        final PixelPngSource pixelPngSource = new PixelPngSource(new File(path));
        final PixelPicGeneInfo pixelPicGeneInfo = new PixelPicGeneInfo(pixelPicGeneConfig, pixelPngSource);
        System.out.println(pixelPicGeneInfo);
        final BufferedImage image = PixelPngDrawer.geneRatePixelPng(pixelPicGeneInfo);
        ImageIO.write(image, "png", new FileOutputStream(path + ".png"));
    }

    @Test
    public void testRec() throws IOException {
        final String filepath = path + ".png";
        final boolean b = PixelPicHandle.convertPixelPicToFile(filepath, filepath + ".dir");
        System.out.println(b);
    }

}
