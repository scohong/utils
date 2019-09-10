package com.scohong.entity.pianchangDO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: scohong
 * @Date: 2019/8/13 11:04
 * @Description:
 */
@Data
public class Plot implements Serializable {
    private int placeId;
    private String placeCname;
    private String placeEname;
    private double lat;
    private double lng;
    private int assType;
    private int assedId;
    private String assedCname;
    private String assedEname;
    private int areaId;
    private String areaCname;
    private String areaEname;
    private int upLevelAreaId;
    private String upLevelAreaCname;
    private String upLevelAreaEname;
    private int sceneId;
    private String sceneName;
    private int episode;
    private int position;
    private String coverPath;
    private long milliSecond;

}
