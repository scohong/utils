package com.scohong.entity.junengchi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: scohong
 * @Date: 2019/10/11 21:01
 * @Description:
 */
@Data
public class UserSubmission {
    private int id;
    private String programName;
    private String shopName;
    private String address;
    private String images;
    private String shopIntroduction;
    private int userId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp uploadAt;
    private String types;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp checkAt;
    private int status;

    public UserSubmission() {
    }

    public UserSubmission(String programName, String shopName, String address, String images,
                          String shopIntroduction, int userId, Timestamp uploadAt) {
        this.programName = programName;
        this.shopName = shopName;
        this.address = address;
        this.images = images;
        this.shopIntroduction = shopIntroduction;
        this.userId = userId;
        this.uploadAt = uploadAt;
    }

}
