package com.scohong;

/**
 * @Author: scohong
 * @Date: 2019/8/26 14:44
 * @Description:
 */
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author scohong
 */
@SpringBootApplication
@MapperScan(value = {"com.scohong.dao"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

