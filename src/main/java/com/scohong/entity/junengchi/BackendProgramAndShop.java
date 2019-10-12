package com.scohong.entity.junengchi;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: scohong
 * @Date: 2019/10/11 20:36
 * @Description:
 */
@Data
public class BackendProgramAndShop implements Serializable {
    private static final long serialVersionUID = 5865L;
    private int id;
    private int programId;
    private int shopId;
    private String programName;
    private String shopName;
    private String city;
    private String address;
    private String introduction;
    private String foods;
    private String episode;
    private String appearTime;
    private String images;
    private String thumbnails;
    private String startTime;
    private String endTime;

    public BackendProgramAndShop(String programName,String shopName, String introduction, String foods, String episode,
                                 String appearTime,String startTime,String endTime) {
        this.programName = programName;
        this.shopName = shopName;
        this.introduction = introduction;
        this.foods = foods;
        this.episode = episode;
        this.appearTime = appearTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
