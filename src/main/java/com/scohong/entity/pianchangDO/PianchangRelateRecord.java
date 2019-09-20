package com.scohong.entity.pianchangDO;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/9/12 13:30
 * @Description:
 */
@Data
public class PianchangRelateRecord {
    private int programId;
    private String programName;
    private int placeId;
    private String placeName;
    private String address;
    private String description;
    private String thumbImages;
    private String images;
    private String appearTime;
    private int episode;
}
