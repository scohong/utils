package com.scohong.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
}
