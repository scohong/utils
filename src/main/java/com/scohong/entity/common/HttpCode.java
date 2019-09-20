package com.scohong.entity.common;


/**
 * @Author: scohong
 * @Date: 2019/7/10 14:17
 * @Description:
 */
public enum HttpCode {

    // 成功
    SUCCESS(200),

    // 失败
    FAIL(400),

    // 未认证（签名错误）
    UNAUTHORIZED(401),

    // 接口不存在
    NOT_FOUND(404),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    public int code;

    HttpCode(int code){
        this.code = code;
    }
}
