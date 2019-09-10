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
public class Result implements Serializable {
    private Movie movie;
    private List<ImgInfos> imgInfos;
    private int favoriteId;
}
