package com.scohong.utils;

/**
 * @Author: scohong
 * @Date: 2019/8/13 14:32
 * @Description:
 */
public class StringUtils {
    /**
     * 替换图片名称的特殊字符，避免服务器路径读取错误
     * @param str
     * @return
     */
    public static String strFormat(String str) {
        return  str.replaceAll("[/:*?\"<>|'’‘ ]", "_");
    }

}
