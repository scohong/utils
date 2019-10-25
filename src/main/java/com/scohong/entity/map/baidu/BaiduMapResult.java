package com.scohong.entity.map.baidu;

/**
 * @Author: scohong
 * @Date: 2019/10/11 16:59
 * @Description:
 */
public class BaiduMapResult {
    private int status;
    private Result result;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaiduMapResult{" +
                "status=" + status +
                ", result=" + result +
                ", message='" + message + '\'' +
                '}';
    }
}
