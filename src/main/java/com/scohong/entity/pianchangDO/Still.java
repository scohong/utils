package com.scohong.entity.pianchangDO;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/9/9 14:38
 * @Description:
 */
@Data
public class Still {
    private int sceneId;
    private int sceneDetailId;
    private String description;
    private String picPath;
    private boolean cover;
    private boolean scene;
}
