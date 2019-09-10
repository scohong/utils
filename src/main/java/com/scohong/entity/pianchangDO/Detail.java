package com.scohong.entity.pianchangDO;

import lombok.Data;

import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/9/9 14:34
 * @Description:
 */
@Data
public class Detail {
    private int id;
    private int sceneId;
    private int episode;
    private int position;
    private String description;
    private String tips;
    private double lat;
    private double lng;
    private boolean pinpoint;
    private List<Person> persons;
    private List<Still> stills;
}
