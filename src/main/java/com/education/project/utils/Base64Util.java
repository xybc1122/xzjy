package com.education.project.utils;

import java.util.Base64;

public class Base64Util {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();


    public static String encoder(String text) {
        //Base64 加密
        return encoder.encodeToString(text.getBytes());
    }


    public static String decode(String text) {
        //Base64 解密
        byte[] decoded = decoder.decode(text);
        return new String(decoded);
    }
}
