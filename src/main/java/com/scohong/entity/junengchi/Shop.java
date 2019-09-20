package com.scohong.entity.junengchi;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/9/20 15:08
 * @Description:
 */
@Data
public class Shop {
    private int shopId;
    private String shopName;
    private String city;
    private String address;
    private String introduction;
    private String coverPic;
    private String thumbCoverPic;
}
