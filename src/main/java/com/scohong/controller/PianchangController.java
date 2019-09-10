package com.scohong.controller;

import com.scohong.Main;
import com.scohong.dao.PianchangDao;
import com.scohong.entity.pianchangDO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * @Author: scohong
 * @Date: 2019/9/10 09:28
 * @Description:
 */
@RestController
public class PianchangController {
    @Autowired
    PianchangDao pianchangDao;

    @GetMapping("/data")
    public String getData() {
        //新版09-09
        File[] files = new File("E:\\片场\\newMovies\\").listFiles();
        for (File file : files) {
            try {
                Movie movie = Main.pianchang2JsonToEntity(file);
                System.out.println(movie.getCname()+ " " + movie.getType());
                //添加新电影
                pianchangDao.insertMovie(movie);
                //添加新场景
//                String movieName = movie2.getData().getMovie().getCname();
//                List<Plot> plots = movie2.getData().getMovie().getPlots();
//                for (Plot p:plots) {
//                    pianchangDao.insertPlot(p,movieName);
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "hohoho";
    }
}
