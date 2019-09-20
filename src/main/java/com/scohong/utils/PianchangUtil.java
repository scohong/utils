package com.scohong.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scohong.entity.Files.ReadFiles;
import com.scohong.entity.common.Response;
import com.scohong.entity.common.StringUtils;
import com.scohong.entity.pianchangDO.Movie;
import com.scohong.entity.pianchangDO.Plot;
import com.scohong.images.DownloadImages;

import java.io.File;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/12 14:59
 * @Description:
 */
public class PianchangUtil {
    public static String secondTimeFormat(int second) {
        String minute = second/60 + "\'";
        String sec = (second % 60) +"\'\'";
        return minute + sec;
    }

//    public static void pianchangDownLoad(String savePath,String filePath) throws Exception {
//        savePath = "E:\\片场\\images\\";
//        File[] files = new File("E:\\片场\\tmp").listFiles();
//        for (File file : files
//        ) {
//            //将文件内容（json string)转换成为实体类
//            Response response = pianchangJsonToEntity(file);
//            //影片名称
//            String cNname = response.getData().getMovie().getCname();
//            cNname = StringUtils.strFormat(cNname);
//            System.out.println(file.getName());
//            System.out.println("开始:"+cNname);
//            // 文件夹以影片名称命名，不存在则创建
//            File dir = new File(savePath.concat(cNname));
//            if (!dir.isFile()) {
//                dir.mkdir();
//            }
//            String saveDir = dir.getAbsolutePath();
//            //影片封面
//            String coverPath = response.getData().getMovie().getCoverPath();
//            DownloadImages.download(coverPath, cNname.concat(".jpg"), saveDir);
//            //取景地
//            List<Plot> plots = response.getData().getMovie().getPlots();
//            int i = 0;
//            for (Plot plot : plots
//            ) {
//                // 场景名称
//                String pName = plot.getPlaceCname();
//                pName = StringUtils.strFormat(pName);
//                // 场景介绍
//                String sName = plot.getSceneName();
//                sName = StringUtils.strFormat(sName);
//                // 场景图
//                String cPath = plot.getCoverPath();
//                //存储到本地
//                //文件名 空格替换
//                DownloadImages.download(cPath, pName.concat("-").concat(sName).concat(".jpg"), saveDir);
//                i++;
//            }
//            System.out.println(cNname + "下载完成，共有" + i + "个场景");
//        }
//    }


    public static Response pianchangJsonToEntity(File file) throws java.io.IOException {
        String jsonString = ReadFiles.readFileByLines(file.getAbsolutePath());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, Response.class);
    }

    public static Movie pianchang2JsonToEntity(File file) throws java.io.IOException {
        String jsonString = ReadFiles.readFileByLines(file.getAbsolutePath());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, Movie.class);
    }
}
