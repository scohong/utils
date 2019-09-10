package com.scohong.entity.pianchangDO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: scohong
 * @Date: 2019/8/13 10:58
 * @Description:
 */
@Data
public class ImgInfos implements Serializable {
    private String id;
    private String format;
    private String colorModel;
    private String size;
    private int frameNumber;
    private int width;
    private int height;
}
