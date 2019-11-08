package com.scohong.entity.bucket;

import lombok.Data;

/**
 * @Author: scohong
 * @Date: 2019/11/4 21:52
 * @Description:
 */
@Data
public class BucketResponse {
    private int status;
    private int error_code;
    private BucketData data;
}
