package com.scohong;

import com.scohong.dao.PianchangDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;

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

}
