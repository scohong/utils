package com.scohong.entity.junengchi;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: scohong
 * @Date: 2019/9/18 20:31
 * @Description:
 */
@Data
public class Program implements Serializable {
    private static final long serialVersionUID = 120L;
    private int programId;
    private String programName;
    private String programAlias;
    private String types;
    private String introduction;
    private int isHot;
    private float hot;
    private int shopNums;
    private String coverPic;
    private String verticalCoverPic;
    private String thumbCoverPic;
    private String actor;
    private String label;
}
