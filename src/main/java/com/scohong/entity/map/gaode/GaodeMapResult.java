package com.scohong.entity.map.gaode;

import lombok.Data;

import java.util.List;

/**
 * @Author: scohong
 * @Date: 2019/10/25 11:33
 * @Description:
 */
@Data
public class GaodeMapResult {
    private int status;
    private String info;
    private int infocode;
    private int count;
    private List<Geocodes> geocodes;

}
