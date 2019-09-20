package com.scohong.entity.pianchangDO;

import lombok.Data;

import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/9 11:49
 * @Description:
 */
@Data
public class PlaceDetail {
    private int id;
    private String cname;
    private String ename;
    private String oname;
    private String alias;
    private String coverPath;
    private String coverSource;
    private String mapPath;
    private double lat;
    private double lng;
    private int areaId;
    private String caddress;
    private String eaddress;
    private String addressTips;
    private int dispark;
    private String disparkRemark;
    private List<Integer> categories;
    private int assedId;
    private int assType;
    private String satellitePath;
    private String satelliteDesc;
    private List<RealGraphics> realGraphics;
    private String phone;
    private String rmmDesc;
    private String tips;
    private String description;
    private String createTime;
    private String staticMapUrl;
    private long milliSecond;
    private String areaCname;
    private String areaEname;
    private int level3Id;
    private String level3Cname;
    private String level3Ename;
    private int level2Id;
    private String level2Cname;
    private String level2Ename;
    private int level1Id;
    private String level1Cname;
    private String level1Ename;
    private int level0Id;
    private String level0Cname;
    private String level0Ename;
    private String assedCname;
    private String assedEname;
    private String assedCoverPath;
    private String asses;
    private List<Scene> scenes;
    private String movies;
    private String staticMapUrlAss;

}
