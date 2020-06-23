package com.education.project.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CourseOrderVo {


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;
}
