package com.scohong.config;

import org.apache.catalina.connector.Connector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

/**
 * @Author: scohong
 * @Date: 2019/8/8 14:52
 * @Description:
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    public static final String separator= File.separator;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //上传的图片在D盘下的OTA目录下，访问路径如：http://localhost:8081/OTA/d3cf0281-bb7f-40e0-ab77-406db95ccf2c.jpg
        //其中images表示访问的前缀。"file:E:\剧能吃\data\"是文件真实的存储路径
        //本地配置

        registry.addResourceHandler("/images/**").addResourceLocations("file:E:\\剧能吃\\data\\");
        registry.addResourceHandler("/shops/**").addResourceLocations("file:E:\\剧能吃\\data\\shops\\");
 //        registry.addResourceHandler("/images/**").addResourceLocations("file:D:\\剧能吃-备份数据\\");
        registry.addResourceHandler("/video/**").addResourceLocations("file:E:\\剧能吃\\video\\");
        registry.addResourceHandler("/gif/**").addResourceLocations("file:E:\\剧能吃\\gif\\");
        registry.addResourceHandler("/backend/**").addResourceLocations("file:E:\\test\\");
        /**本地纪录片资源映射地址*/
        registry.addResourceHandler("/record/**").addResourceLocations("file:D:\\剧能吃-纪录片\\");
        //服务器配置
//        registry.addResourceHandler("/images/**").addResourceLocations("file:/images/");
//        registry.addResourceHandler("/video/**").addResourceLocations("file:/video/");
    }
    //监听端口
    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(8001);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(8092);
        return connector;
    }
}
