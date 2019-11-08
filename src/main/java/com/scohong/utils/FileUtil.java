package com.scohong.utils;

import com.scohong.entity.common.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.*;

/**
 * @Author: scohong
 * @Date: 2019/7/16 10:10
 * @Description:
 */
public class FileUtil {
    /**
     * 文件保存方法,返回上一级路径和文件名
     * @param file
     * @param destination
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String saveFile(MultipartFile file, String destination) throws IllegalStateException, IOException {
        // 获取上传的文件名称，并结合存放路径，构建新的文件名称
        String filename = file.getOriginalFilename();
        File filepath = new File(destination, filename);
        String parentPath = filepath.getParent();
        // 判断路径是否存在，不存在则新创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        // 将上传文件保存到目标文件目录
        String resFilePath = destination + File.separator + filename;
        file.transferTo(new File(resFilePath));
        return resFilePath;
    }

    /**
     * @Description: 判断文件是否是图片类型，根据ContentType值进行判断
     * @param file
     * @return
     */
    public static boolean imageJudge(MultipartFile file) {
        String fileType= file.getContentType();
        String type = fileType.split("/")[0];
        return type.equals("image");
    }


    /**
     * 复制文件
     * @param src
     * @param target
     */
    public static void copyFile(String src,String target)
    {
        File srcFile = new File(src);
        File targetFile = new File(target);
        try {
            InputStream in = new FileInputStream(srcFile);
            OutputStream out = new FileOutputStream(targetFile);
            byte[] bytes = new byte[1024];
            int len = -1;
            while((len=in.read(bytes))!=-1)
            {
                out.write(bytes, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给新的文件名，创建父级目录
     */
    public static void mkParentDir(File newFile) {
        //拼接新的目录
        String programDir = newFile.getParent();
        if (!new File(programDir).isDirectory()) {
            //创建文件夹
            new File(newFile.getParent()).mkdirs();
        }
    }

    /**
     * 检查视频文件大小
     * @param filePath
     */
    public static void checkVideoFileSize(String filePath) {
        //压缩文件
        File file = new File(filePath);
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        int s1 = 0;
        int s2 = 0;
        int s3 = 0;
        int s4 = 0;
        int s5 = 0;
        int s6 = 0;
        int s7 = 0;
        for (File f : file.listFiles()
        ) {
            if (!f.isDirectory()) {
                continue;
            }
            for (File ff : f.listFiles()) {
                long size = ff.length() / 1024 / 1024;
                min = min < size ? min : size;
                if (min == 0) {
                    System.out.println(ff.getAbsolutePath());
                }
                max = max > size ? max : size;
                if (size < 3) {
                    s1++;
                } else if (size < 5) {
                    s2++;
                } else if (size < 8) {
                    s3++;
                } else if (size < 10) {
                    s4++;
                } else if (size < 15) {
                    s5++;
                } else if (size < 20) {
                    s6++;
                } else {
                    s7++;
                    System.out.println(ff.getAbsolutePath());
                }
//                String newFileName = ff.getAbsolutePath().replaceAll("shops", "shops-720");
//                String dir = new File(newFileName).getParent();
//                if (!new File(dir).isDirectory()) {
//                    new File(dir).mkdirs();
//                }
//                System.out.println(newFileName);
//                try {
//                    Thumbnails
//                            .of(ff)
//                            .size(1280,720)
//                            .outputQuality(0.8)
//                            .toFile(newFileName);
//                } catch (IOException e) {
//                    FileUtil.copyFile(ff.getAbsolutePath(),newFileName.replaceAll("program-1104","program-bug"));
//                    e.printStackTrace();
//                }
            }
        }
        System.out.println("<3M:"+s1);
        System.out.println("<5M:"+s2);
        System.out.println("<8M:"+s3);
        System.out.println("<10M:"+s4);
        System.out.println("<15M:"+s5);
        System.out.println("<20M:"+s6);
        System.out.println(">20:"+s7);
        System.out.println("min:"+min);
        System.out.println("max:"+max);
    }

    /**
     * 检查图片文件大小
     * @param filePath
     */
    public static void checkImageFileSize(String filePath) {
        //压缩文件
        File file = new File(filePath);
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        int s1 = 0;
        int s2 = 0;
        int s3 = 0;
        int s4 = 0;
        int s5 = 0;
        int s6 = 0;
        int s7 = 0;
        for (File f : file.listFiles()
        ) {
            if (!f.isDirectory()) {
                continue;
            }
            for (File ff : f.listFiles()) {
                long size = ff.length() / 1024 ;
                min = min < size ? min : size;
                if (min == 0) {
                    System.out.println(ff.getAbsolutePath());
                }
                max = max > size ? max : size;
                if (size < 50) {
                    s1++;
                } else if (size < 100) {
                    s2++;
                } else if (size < 150) {
                    s3++;
                } else if (size < 200) {
                    s4++;
                } else if (size < 250) {
                    s5++;
                } else if (size < 300) {
                    s6++;
                } else {
                    s7++;
                    System.out.println(ff.getAbsolutePath());
                }
//                String newFileName = ff.getAbsolutePath().replaceAll("shops", "shops-720");
//                String dir = new File(newFileName).getParent();
//                if (!new File(dir).isDirectory()) {
//                    new File(dir).mkdirs();
//                }
//                System.out.println(newFileName);
//                try {
//                    Thumbnails
//                            .of(ff)
//                            .size(1280,720)
//                            .outputQuality(0.8)
//                            .toFile(newFileName);
//                } catch (IOException e) {
//                    FileUtil.copyFile(ff.getAbsolutePath(),newFileName.replaceAll("program-1104","program-bug"));
//                    e.printStackTrace();
//                }
            }
        }
        System.out.println("<50k:"+s1);
        System.out.println("<100k:"+s2);
        System.out.println("<150k:"+s3);
        System.out.println("<200k:"+s4);
        System.out.println("<250k:"+s5);
        System.out.println("<300k:"+s6);
        System.out.println(">300:"+s7);
        System.out.println("min:"+min);
        System.out.println("max:"+max);
    }

    /**
     * 检查图片尺寸大小
     * @param filePath
     */
    public static Map<String, Integer> map = new HashMap<>();
    public static void checkImageDimenSize(String filePath) {
        //压缩文件
        File file = new File(filePath);
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        List<Float> rateList = new ArrayList<>();
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
            int width = reader.getWidth(0);
            int height = reader.getHeight(0);
            float rate = (float) width / height;
            String aa = String.valueOf(rate);
            String a = "";
            if (aa.length() >= 4) {
                a = aa.substring(0, 4);
            }
            String copyFilePath = filePath.replaceAll("原图", "压缩");
            FileUtil.mkParentDir(new File(copyFilePath));
            if (!map.containsKey(a)) {
                FileUtil.copyFile(filePath,copyFilePath);
                map.put(a,1);
                System.out.println(a);
                System.out.println(filePath);
            }
        } catch (IOException e) {
            System.out.println(filePath);
            e.printStackTrace();
        }
//        int s1 = 0;
//        int s2 = 0;
//        int s3 = 0;
//        int s4 = 0;
//        int s5 = 0;
//        int s6 = 0;
//        int s7 = 0;
//        for (File f : file.listFiles()
//        ) {
//            if (!f.isDirectory()) {
//                continue;
//            }
//            for (File ff : f.listFiles()) {
//                long size = ff.length() / 1024 ;
//                min = min < size ? min : size;
//                if (min == 0) {
//                    System.out.println(ff.getAbsolutePath());
//                }
//                max = max > size ? max : size;
//                if (size < 50) {
//                    s1++;
//                } else if (size < 100) {
//                    s2++;
//                } else if (size < 150) {
//                    s3++;
//                } else if (size < 200) {
//                    s4++;
//                } else if (size < 250) {
//                    s5++;
//                } else if (size < 300) {
//                    s6++;
//                } else {
//                    s7++;
//                    System.out.println(ff.getAbsolutePath());
//                }
//            }
//        }
//        System.out.println("<50k:"+s1);
//        System.out.println("<100k:"+s2);
//        System.out.println("<150k:"+s3);
//        System.out.println("<200k:"+s4);
//        System.out.println("<250k:"+s5);
//        System.out.println("<300k:"+s6);
//        System.out.println(">300:"+s7);
//        System.out.println("min:"+min);
//        System.out.println("max:"+max);
    }

    public static void transferToMd5(String sourceDir,String targetDir) {
        //压缩文件
        File sDir = new File(sourceDir);
        File tDir = new File(targetDir);
        if (!tDir.isDirectory()) {
            tDir.mkdirs();
        }
        for (File f : sDir.listFiles()) {
            String oriName = f.getName();
            String md5Name = DigestUtils.md5Hex(oriName);
            //获取文件后缀
            String suff = oriName.substring(oriName.length() - 4);
            System.out.println(suff);
            System.out.println(sourceDir+oriName);
            System.out.println(tDir+md5Name + suff);
            FileUtil.copyFile(sourceDir+oriName,tDir+File.separator+md5Name + suff);
        }
    }

}
