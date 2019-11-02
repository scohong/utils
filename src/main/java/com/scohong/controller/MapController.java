package com.scohong.controller;

import com.alibaba.fastjson.JSONObject;
import com.scohong.dao.ShopDao;
import com.scohong.entity.map.baidu.BaiduMapResult;
import com.scohong.entity.map.ShopMap;
import com.scohong.entity.map.gaode.GaodeMapResult;
import com.scohong.entity.map.gaode.Geocodes;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/10/25 11:13
 * @Description:
 */
@RestController
@RequestMapping("/map")
@Slf4j
@Api(tags = "地图相关")
public class MapController {
    @Autowired
    ShopDao shopDao;

    /**
     * 百度api更新商家经纬度
     * 5000qps/d
     *
     * @return
     */
    @GetMapping("/updateShopLocByBaidu")
    public String getMap(@RequestParam int id) {
        RestTemplate restTemplate = new RestTemplate();
        List<ShopMap> frames = shopDao.getAllShopByMap(0);
        int i = 0;
        for (ShopMap frame : frames
        ) {
            i++;
            String url = "https://api.map.baidu.com/geocoding/v3/?output=json&ak=9zu97erhMPhNBhpuWDEaeOlGX9WXQqzh&callback=showLocation&" +
                    "address=";
            String address = frame.getAddress();
            String fullUrl = url + address;
            String response = restTemplate.getForObject(fullUrl, String.class);
            JSONObject result = null;
            try {
                result = JSONObject.parseObject(response);
                System.out.println("结果:" + response);
            } catch (Exception e) {
                response = response.substring(27, response.length() - 1);
                BaiduMapResult bd = JSONObject.parseObject(response, BaiduMapResult.class);
                if (bd.getStatus() != 0) {
                    continue;
                }
                frame.setLongitude(bd.getResult().getLocation().getLng());
                frame.setLatitude(bd.getResult().getLocation().getLat());
                frame.setConfidence(bd.getResult().getConfidence());
                frame.setComprehension(bd.getResult().getComprehension());
                frame.setPrecise(bd.getResult().getPrecise());
                frame.setLevel(bd.getResult().getLevel());
                shopDao.updateAddress(frame);
            }
//                }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }

    /**
     * 高德api更新商家经纬度
     * 6000qps/d
     *
     * @return
     */
    @GetMapping("/updateShopLocByGaode")
    public String getGaodeMap(@RequestParam int id) {
        RestTemplate restTemplate = new RestTemplate();
        List<ShopMap> frames = shopDao.getAllShopByMap(id);
        int count = 0;
        for (ShopMap frame : frames
        ) {
            String url = "https://restapi.amap.com/v3/geocode/geo?key=c227549a740e0f19f9a2213f86538dba&" +
                    "address=";
            String address = frame.getAddress();
            String fullUrl = url + address;
            String response = restTemplate.getForObject(fullUrl, String.class);
            System.out.println(response);
            try {
                GaodeMapResult gdList = JSONObject.parseObject(response, GaodeMapResult.class);
                if (gdList.getCount() != 1) {
                    log.info("查询失败"+frame.getId()+":"+frame.getAddress());
                    continue;
                }
                Geocodes gd = gdList.getGeocodes().get(0);
                String[] loc = gd.getLocation().split(",");
                String lat = loc[1];
                String lng = loc[0];
                System.out.println(lat);
                System.out.println(lng);
                frame.setLongitude(lng);
                frame.setLatitude(lat);
                int i = shopDao.updateAddress(frame);
                if (i != 1) {
                    count++;
                    log.info("插入失败");
                }
                System.out.println(frame.getId() + ":" + frame.getAddress());
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("插入失败sum：" + count);
        return "ok";
    }
}
