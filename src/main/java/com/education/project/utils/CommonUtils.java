package com.education.project.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * 常用工具类的封装，md5,uuid等
 */
public class CommonUtils {


    /**
     * md5常用工具类
     *
     * @param data
     * @return
     */
    public static String MD5(String data) {
        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String covPrice(String price) {
        BigDecimal decimal = new BigDecimal(price);
        BigDecimal res = decimal.multiply(new BigDecimal(100));
        return res.intValue() + "";
    }

    public static void main(String[] args) {
        System.out.println(covPrice("2299.00"));
    }

}
