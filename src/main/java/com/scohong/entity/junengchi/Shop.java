package com.scohong.entity.junengchi;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/9/20 15:08
 * @Description:
 */
@Data
public class Shop {
    private int shopId;
    private String shopName;
    private String city;
    private String address;
    private String introduction;
    private String coverPic;
    private String thumbCoverPic;

    public Shop() {
    }

    public Shop(String shopName, String city, String address, String introduction) {
        this.shopName = shopName;
        this.city = city;
        this.address = address;
        this.introduction = introduction;
    }
}
