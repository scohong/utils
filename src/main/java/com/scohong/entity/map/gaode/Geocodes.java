package com.scohong.entity.map.gaode;

import lombok.Data;

import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/10/25 11:35
 * @Description:
 */
@Data
public class Geocodes {
    private String formatted_address;
    private String country;
    private String province;
    private String citycode;
    private String city;
    private String district;
    private List township;
    private Neighborhood neighborhood;
    private Building building;
    private int adcode;
    private List street;
    private List number;
    private String location;
    private String level;
}
