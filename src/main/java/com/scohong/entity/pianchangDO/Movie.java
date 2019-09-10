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
public class Movie implements Serializable {
    private int id;
    private String cname;
    private String ename;
    private String coverPath;
    private int year;
    private int countryId;
    private String countryCname;
    private String countryEname;
    private int type;
    private List<Integer> categories;
    private List<Integer> placeIds;
    private List<Area> areas;
    private long milliSecond;
    private String overview;
    private List<Plot> plots;
    private String staticMapUrl;
    private boolean series;
    private int favoriteId;
    private List<ImgInfos> imgInfos;
    private List<PlaceDetail> placeDetails;

}
