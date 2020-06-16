package com.education.project.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {


    public static String getStudentId(HttpServletRequest request) {
        return (String) request.getAttribute("studentId");
    }

}
