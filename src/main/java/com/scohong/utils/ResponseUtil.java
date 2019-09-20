package com.scohong.utils;


import com.scohong.entity.common.HttpCode;
import com.scohong.entity.common.Response;

/**
 * @Author: scohong
 * @Date: 2019/7/10 14:20
 * @Description:
 */

public class ResponseUtil {

    private final static String SUCCESS = "success";
    private final static String ERROR = "failed";

    public static <T> Response<T> ok(){
        return new Response<T>().setCode(HttpCode.SUCCESS).setMsg(SUCCESS);
    }

    public static <T> Response<T> ok(T data){
        return new Response<T>().setCode(HttpCode.SUCCESS).setMsg(SUCCESS).setResult(data);
    }

    public static <T> Response<T> error(){
        return new Response<T>().setCode(HttpCode.FAIL).setMsg(ERROR);
    }


    public static <T> Response<T> message(int code, String msg){
        return new Response<T>().setCode(code).setMsg(msg);
    }

    public static <T> Response<T> message(int code, String msg, T result){
        return new Response<T>().setCode(code).setMsg(msg).setResult(result);
    }
}
