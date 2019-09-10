package com.scohong.entity.common;

/**
 * @Author: scohong
 * @Date: 2019/8/13 14:32
 * @Description:
 */
public class StringUtils {
    public static String strFormat(String str) {
        return  str.replaceAll("/|:|\\*|\\?|\"|<|>|\\|| ", "_");
    }
}
