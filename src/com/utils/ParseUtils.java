package com.utils;

/**
 * 转换工具类
 * create by Mrzhang on 2018/12/5
 */
public class ParseUtils {

    public static long parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

    public static long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException exception) {
            return 0;
        }
    }

}
