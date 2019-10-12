package com.scohong.entity.common;


import com.scohong.entity.junengchi.Pagination;

/**
 * @Author: scohong
 * @Date: 2019/7/10 14:19
 * @Description:
 */

public class Response<T> {
    private int code;
    private String msg;
    private T result;
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public Response<T> setCode(HttpCode httpCode){
        this.code = httpCode.code;
        return this;
    }

    public Response<T> setPagination(Pagination pagination) {
        this.pagination = pagination;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getResult() {
        return result;
    }

    public Response<T> setResult(T result) {
        this.result = result;
        return this;
    }
}