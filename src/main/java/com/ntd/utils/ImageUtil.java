package com.ntd.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import org.springframework.util.StringUtils;

/**
 * Created by nongtiedan on 2016/9/2.
 */
public class ImageUtil {

    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param output 输出流
     * @param fontName 字体名称
     * @param fontStyle 字体样式
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText, String srcImageFile,String destImageFile, OutputStream output,
                                        String fontName, int fontStyle, Color color, int fontSize, int x,
                                        int y, float alpha) {
        try {
            if(StringUtils.isEmpty(pressText)){
                return ;
            }
            if(pressText.length() > 2){
                pressText = pressText.substring(0, 2) ;
            }
            int drawX = 0;
            int drawY = 0;
            URL url = new URL(srcImageFile);
            url.openConnection().connect();
            InputStream is = url.openStream();
            Image src = ImageIO.read(is);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g.dispose();
            g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setFont(new Font(fontName, Font.PLAIN, fontSize));
            BasicStroke pen = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g.setStroke(pen);
            // 在指定坐标绘制水印文字
            if(pressText.length() == 1){
                drawX = 4;
                drawY = 22;
                g.setColor(Color.BLACK);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
                g.drawString(pressText, drawX+1, drawY);
                g.setColor(Color.WHITE);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
                g.drawString(pressText, drawX, drawY);
            }
            if(pressText.length() == 2){
                drawX = 14;
                drawY = 17;
                //设置黑色半透明阴影
                g.setColor(Color.BLACK);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
                g.drawString(pressText.substring(0, 1), drawX+1, drawY+1);
                //写字
                g.setColor(Color.WHITE);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
                g.drawString(pressText.substring(0, 1), drawX, drawY);

                drawX = 30;
                drawY = 17;
                g.setColor(Color.BLACK);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
                g.drawString(pressText.substring(1, 2), drawX+1, drawY+1);
                g.setColor(Color.WHITE);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
                g.drawString(pressText.substring(1, 2), drawX, drawY);
            }

            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
            //ImageIO.write((BufferedImage) image, "png", output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ByteArrayOutputStream out = null;
        String picUrl = "http://img5.cache.netease.com/liveshow//image/home11.png";
        try {
            out = new ByteArrayOutputStream();
            ImageUtil.pressText("有道", picUrl, "D:/a.png", out, "simsun", 0, Color.WHITE, 13, 0, 0, 0f);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }

        }
    }

}
