package com.scohong.images;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: scohong
 * @Date: 2019/8/13 10:51
 * @Description:
 */
public class DownloadImages {

    /**
     * 从指定url下载图片到本地
     *
     * @param urlString url
     * @param filename 存储文件名
     * @param savePath 存储路径
     * @throws Exception
     */
    public static void download(String urlString, String filename,String savePath) throws Exception {
        if (urlString == null || urlString.length() == 0 || "".equals(urlString) ) {
            return;
        }
        System.out.println(urlString);
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

}
