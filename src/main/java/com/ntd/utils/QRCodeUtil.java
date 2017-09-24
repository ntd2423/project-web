package com.ntd.utils;

import com.google.common.collect.Maps;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 二维码工具类
 */
public final class QRCodeUtil {

    private static final Log logger = LogFactory.getLog(QRCodeUtil.class);

     /*
      * contest : 二维码内容
      * width : 二维码宽
      * height ：二维码高
      */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static BufferedImage encode(String content, int width, int height) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            Map hints = Maps.newHashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);//白边间隙
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            logger.error("QR Code Util Encode Error!");
            e.printStackTrace();
        }
        return null;
    }

    //生成二维码图片
    public static String createQrCodeUrl(String content, int width, int height) {
        checkNotNull(content);
        try {
            BufferedImage bufferedImage = encode(content, width, height);
            checkNotNull(bufferedImage);
            File file = new File("D://" + System.currentTimeMillis() + ".jpg");
            ImageIO.write(bufferedImage, "jpg", file);

        } catch (NullPointerException | IOException e) {
            logger.error("getQrCodeUrl write image error ", e);
        }
        return null;

    }

    public static String createQrCodeUrl(String content) {

        return createQrCodeUrl(content, 400, 400);

    }

    public static void main(String[] args) throws Exception {
        String text = "http://www.bobo.com";
        createQrCodeUrl(text);
    }
}