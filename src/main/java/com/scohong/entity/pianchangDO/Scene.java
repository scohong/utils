package com.scohong.entity.pianchangDO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/8/13 10:58
 * @Description:
 */
@Data
public class Scene implements Serializable {
    private int movieId;
    private String movieCname;
    private String movieEname;
    private String coverPath;
    private int year;
    private int countryId;
    private String countryCname;
    private String countryEname;
    private int type;
    private List<Integer> categories;
    private int placeId;
    private int sceneId;
    private String sceneXys;
    private String sceneXyDesc;
    private String sceneOverride;
    private List<Detail> details;
    private String staticMapUrl;
    private boolean series;


}
