package com.scohong.entity.pianchangDO;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: scohong
 * @Date: 2019/8/13 10:55
 * @Description:
 */
@Data
public class Response implements Serializable {
    private String code;
    private String msg;
    private Result data;
}
