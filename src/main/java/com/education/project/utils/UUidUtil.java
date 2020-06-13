package com.education.project.utils;

import java.util.UUID;

public class UUidUtil {



    public static String getUUid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
