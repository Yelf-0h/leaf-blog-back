package com.yecheng.leafblogback.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


public class ImageUtil {


    /**
     * 网络图片下载
     *
     * @param imageUrl   图片url
     * @param formatName 文件格式名称
     * @param localFile  下载到本地文件
     * @return 下载是否成功
     */
    public static boolean downloadImage(String imageUrl, String formatName, File localFile) {
        boolean isSuccess = false;
        URL url = null;
        try {
            url = new URL(imageUrl);
            isSuccess = ImageIO.write(ImageIO.read(url), formatName, localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 下载 海康返回图片地址,到CDN TODO 海康抓图的图片有效期？？？没有定时清理???
     *
     * @param webUrl 网路图片地址
     * @return 相对地址/xx/xx.png
     */
    public static String downHikWebPicToCdn(String webUrl) {
        try {
            InputStream inputStream = null;
            HttpURLConnection httpURLConnection = null;
            String remotePath = "";
            URL url = new URL(webUrl);
            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
            }

            // String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + StringUtil.randomString(32) + ".jpg";

            // remotePath = BaseController.getCDNStoragePath("MON-CUT") + fileName;

            return remotePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "图片上传失败";
        }
    }

//    public static void main(String[] args) {
//        String baiduLogoUrl = "http://pic1.win4000.com/wallpaper/2020-04-14/5e95539df2ade.jpg";
//        File localFile = new File("C:\\Users\\Sholybell\\Desktop\\" + "test.jpg");
//        System.out.println(ImageUtil.downloadImage(baiduLogoUrl, "jpg", localFile));
//    }

}
