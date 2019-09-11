package com.scohong.controller;

import com.scohong.Main;
import com.scohong.dao.PianchangDao;
import com.scohong.entity.pianchangDO.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/10 09:28
 * @Description:
 */
@RestController
@Slf4j
public class PianchangController {
    @Autowired
    PianchangDao pianchangDao;


    @GetMapping("/data")
    public String getData() {
        //新版09-09
        //添加场景详情placeDetail
        File[] files = new File("E:\\片场\\newMovies\\").listFiles();
        for (File file : files){
            try {
                Movie movie = Main.pianchang2JsonToEntity(file);
//                System.out.println(movie.getCname()+ " " + movie.getType());
                log.info(""+movie.getId());
                List<PlaceDetail> placeDetails = movie.getPlaceDetails();
                //获取palceDetail数据
                if (placeDetails.size() > 0) {
                    log.info("地点："+placeDetails.get(0).getCname());
                    Scene scene = placeDetails.get(0).getScenes().get(0);
                    //获取scene数据
                    log.info("节目："+scene.getMovieCname());
                    log.info("场景介绍："+scene.getSceneOverride());
                    //detail
                    Detail detail = scene.getDetails().get(0);
                    log.info("details:" + detail.getDescription() );
                    //场景描述，多图
                    Still still = detail.getStills().get(0);
                    log.info("still:" + still.getPicPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "hohoho";
    }
}
