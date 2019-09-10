package com.scohong;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scohong.dao.PianchangDao;
import com.scohong.entity.Files.ReadFiles;
import com.scohong.entity.common.StringUtils;
import com.scohong.entity.pianchangDO.Movie;
import com.scohong.entity.pianchangDO.Plot;
import com.scohong.entity.pianchangDO.Response;
import com.scohong.images.DownloadImages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/8/13 10:51
 * @Description:
 */
@Component
@Slf4j
public class Main {
    public static Main main;
    @Autowired
    PianchangDao pianchangDao;

    public static void main(String[] args) throws Exception {
//       pianchangDownLoad("E:\\片场\\images\\","E:\\片场\\tmp");

    }

    @PostConstruct
    @Transactional
    public void init() {
        //旧版
//        File[] files = new File("E:\\片场\\movieJson").listFiles();
//        for (File file : files) {
//            try {
//                Response response = Main.pianchangJsonToEntity(file);
//                log.info(response.getData().getMovie().getCname());
//                pianchangDao.insertPlace(response.getData().getMovie());
//                String movieName = response.getData().getMovie().getCname();
//                List<Plot> plots = response.getData().getMovie().getPlots();
//                for (Plot p:plots) {
//                    pianchangDao.insertPlot(p,movieName);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        //新版09-09
//        File[] files = new File("E:\\片场\\movieWithPlaces6").listFiles();
//        for (File file : files) {
//            try {
//                Movie movie = Main.pianchang2JsonToEntity(file);
////                log.info(movie.getCname());
//                //添加新电影
//                pianchangDao.insertMovie(movie);
//                //添加新场景
////                String movieName = movie2.getData().getMovie().getCname();
////                List<Plot> plots = movie2.getData().getMovie().getPlots();
////                for (Plot p:plots) {
////                    pianchangDao.insertPlot(p,movieName);
////                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void pianchangDownLoad(String savePath,String filePath) throws Exception {
        savePath = "E:\\片场\\images\\";
        File[] files = new File("E:\\片场\\tmp").listFiles();
        for (File file : files
        ) {
            //将文件内容（json string)转换成为实体类
            Response response = pianchangJsonToEntity(file);
            //影片名称
            String cNname = response.getData().getMovie().getCname();
            cNname = StringUtils.strFormat(cNname);
            System.out.println(file.getName());
            System.out.println("开始:"+cNname);
            // 文件夹以影片名称命名，不存在则创建
            File dir = new File(savePath.concat(cNname));
            if (!dir.isFile()) {
                dir.mkdir();
            }
            String saveDir = dir.getAbsolutePath();
            //影片封面
            String coverPath = response.getData().getMovie().getCoverPath();

            DownloadImages.download(coverPath, cNname.concat(".jpg"), saveDir);
            //取景地
            List<Plot> plots = response.getData().getMovie().getPlots();
            int i = 0;
            for (Plot plot : plots
            ) {
                // 场景名称
                String pName = plot.getPlaceCname();
                pName = StringUtils.strFormat(pName);
                // 场景介绍
                String sName = plot.getSceneName();
                sName = StringUtils.strFormat(sName);
                // 场景图
                String cPath = plot.getCoverPath();
                //存储到本地
                //文件名 空格替换
                DownloadImages.download(cPath, pName.concat("-").concat(sName).concat(".jpg"), saveDir);
                i++;
            }
            System.out.println(cNname + "下载完成，共有" + i + "个场景");
        }
    }

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
