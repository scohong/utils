package com.scohong.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @Author: scohong
 * @Date: 2019/7/25 17:45
 * @Description:
 */
@Slf4j
public class ImageUtil {
    /**
     * @Description: 给图片名称添加前缀
     * @param imagePath 图片地址
     * @param prefix 前缀
     */
    public static void addPrefix(String imagePath,String prefix) {
        File file = new File(imagePath);
        File[] files = file.listFiles();
        for (File image: files) {
            if (!image.isDirectory()) {
                String parentPath = image.getParent();
                log.info(parentPath);
                String oldName = image.getName();
                System.out.println("oldName:" + oldName);
                log.info(parentPath+File.separator+oldName);
                image.renameTo(new File(parentPath+File.separator+prefix+oldName));
            }
        }
    }

    /**
     * 替换图片文件的名称，过滤特殊字符
     * @param imagePath
     */
    public static void replaceStr(String imagePath) {
        File file = new File(imagePath);
        File[] files = file.listFiles();
        for (File dir: files) {
            for (File image: dir.listFiles()
                 ) {
                if (!image.isDirectory()) {
                    String parentPath = image.getParent();
                    String oldName = image.getName();
                    log.info("before:" + oldName);
                    oldName = StringUtils.strFormat(oldName);
                    log.info("after:" + oldName);
                    log.info(parentPath+File.separator+oldName);
                    image.renameTo(new File(parentPath+File.separator+oldName));
                }
            }
        }
    }

    /**
     * 压缩图片，添加t_
     * @param files
     */
    public static void getThumbFileDir(File[] files) {
        log.info(files.length+"");
        for (File aFile: files
        ) {
            if (aFile.isDirectory()) {
                for (File tFile: aFile.listFiles()
                     ) {
                    if (tFile.isFile()) {
                        log.info("压缩");
                        String pre = tFile.getParent();
                        String fileName = tFile.getName();
                        log.info(tFile.getName());
                        if (tFile.getName().indexOf("jpg") != -1) {
                            //创建thumb文件夹
                            File thumbFile = new File(pre);
                            if (!thumbFile.isDirectory()) {
                                thumbFile.mkdir();
                            }
                            //开始压缩图片
                            log.info(pre + "/" + fileName);
                            try {
                                Thumbnails.of(tFile)
                                        .scale(1)
                                        .toFile(pre + "/t_" + fileName);
                                log.info("压缩图片：" + tFile.getName());
                            } catch (IOException e) {
                                log.info("一串");
                                continue;
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public static void pngTojpgDir(File[] files) {
        for (File f : files) {
            if (f.isDirectory()) {
                for (File nf : f.listFiles()
                ) {
                    File nFile = new File(nf.getAbsolutePath().replaceAll("png", "jpg"));
                    try {
                        if (!nFile.isFile()) {
                            Files.copy(nf.toPath(), nFile.toPath());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                continue;
            }
        }
    }

    public static String  pngTojpgFile(File nf) {
        File nFile = new File(nf.getAbsolutePath().replaceAll("png", "jpg"));
        try {
            if (!nFile.isFile()) {
                Files.copy(nf.toPath(), nFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nFile.getAbsolutePath();
    }


    public static String getImageType(File srcFilePath) {
        FileInputStream imgFile;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(srcFilePath);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return null;
        }
        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];
            //gif、png和jfif(jpg)
            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                return "gif";
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                return "png";
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                return "jpg";
            } else {
                return "other";
            }
        } else {
            return "other";
        }
    }
}
