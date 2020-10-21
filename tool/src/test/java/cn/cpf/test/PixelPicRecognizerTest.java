package cn.cpf.test;
import java.awt.image.BufferedImage;
import java.io.File;

import com.github.cpfniliu.tool.pixelpic.*;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.IOException;

public class PixelPicRecognizerTest {

    @Test
    public void testGne() throws IOException {
        final String path = "E:\\res\\FMBS_DEV_BANK\\inner\\clipout\\国开行全量二级分类代码_new.xlsx";
        PixelPicGeneConfig pixelPicGeneConfig = new PixelPicGeneConfig();
        pixelPicGeneConfig.setMargin(12, 1, 100, 34);
        pixelPicGeneConfig.setRowPixelCnt(1500);
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
        final String path = "D:\\Users\\CPF\\Desktop\\微信截图_20201020161743.png";
        PixelPicHandle.convertPixelPicToFile(path, path + ".dir");
    }

}
