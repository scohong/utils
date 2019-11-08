package com.scohong.entity.map;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/10/11 16:33
 * @Description:
 */
@Data
public class ShopMap {
    private String id;
    private String address;
    private String longitude;
    private String latitude;
    private String city;
    private int confidence;
    private int precise;
    private int comprehension;
    private String level;

    public ShopMap() {
    }

    public ShopMap(String id, String address, String longitude, String latitude) {
        this.id = id;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
