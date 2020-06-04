package com.education.project.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {





    public static String getStudentId(HttpServletRequest request){
        return (String) request.getAttribute("studentId");
    }

    public static String getName(HttpServletRequest request){
        return (String) request.getAttribute("name");
    }

    public static Integer getGradeId(HttpServletRequest request){
        return (Integer) request.getAttribute("gradeId");
    }
}
