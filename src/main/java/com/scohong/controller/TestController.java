package com.scohong.controller;

import com.scohong.entity.common.Response;
import com.scohong.utils.ResponseUtil;
import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.Test;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

/**
 * @Author: scohong
 * @Date: 2019/10/17 14:24
 * @Description:
 */
@RestController
public class TestController {
    @Test
    /**
     * 批量使用tinypng压缩图片
     * 慢
     */
    public void tiny() {
        Tinify.setKey("JKqZb0MKDhyYjQtY9VDNNnmbRrKH4wk0");
        String firDir = "E:\\test\\4k\\小美好\\";
        File file = new File(firDir);
        Source source = null;
        try {
            for (File f:file.listFiles()
                 ) {
                source = Tinify.fromFile(f.getAbsolutePath());
                Options options = new Options()
                        .with("method", "fit")
                        .with("width", 1280)
                        .with("height", 720);
                Source resized = source.resize(options);
                resized.toFile(f.getPath()+"gt720_"+f.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void thumbImg() throws Exception {
        Thumbnails
                .of("E:\\test\\4k\\3.1.png")
                .size(1280,720)
                .outputFormat("jpg")
                .outputQuality(0.75)
                .toFile("E:\\test\\4k\\小美好\\gt720_3.1.jpg");
    }

}
