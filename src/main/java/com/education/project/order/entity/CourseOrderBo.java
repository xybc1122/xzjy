package com.education.project.order.entity;


import java.util.List;

public class CourseOrderBo extends CourseOrder {


    private List<String> queryTime;

    private String payState;

    private String  CourseId;

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public List<String> getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(List<String> queryTime) {
        this.queryTime = queryTime;
    }
}
