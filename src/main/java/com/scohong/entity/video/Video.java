package com.scohong.entity.video;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/9/18 11:10
 * @Description:
 */
@Data
public class Video {
    private int frameId;
    private String[] program;
    private String videoStartTime;
    private String videoEndTime;
    private String gifStartTime;
    private String gifEndTime;
}
