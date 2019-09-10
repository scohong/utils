package com.scohong.entity.pianchangDO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: scohong
 * @Date: 2019/8/13 11:03
 * @Description:
 */
@Data
public class Area implements Serializable {
    private int areaId;
    private String areaCname;
    private String areaEname;

}
