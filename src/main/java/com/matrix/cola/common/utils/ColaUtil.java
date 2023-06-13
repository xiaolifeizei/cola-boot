package com.matrix.cola.common.utils;

import java.text.DecimalFormat;

/**
 * 一些通用方法
 *
 * @author : cui_feng
 * @since : 2022-06-13 10:03
 */
public class ColaUtil {
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    private static final int GB = 1024 * 1024 * 1024;

    private static final int MB = 1024 * 1024;

    private static final int KB = 1024;

    /**
     * 文件大小转换
     */
    public static String getSize(long size) {
        String resultSize;
        if (size / GB >= 1) {
            resultSize = DF.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            resultSize = DF.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            resultSize = DF.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }
}
