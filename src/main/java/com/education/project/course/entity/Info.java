package com.education.project.course.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("course_info")
public class Info implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    private String courseId;

    /**
     * 课程描述
     */
    private String courseDesc;

    /**
     * 课程标题
     */
    private String courseTitle;

    /**
     * 课程图片路径
     */
    private String courseUrl;

    /**
     * 课程价格
     */
    private BigDecimal coursePrice;

    /**
     * 课程开始时间
     */
    private Date courseStartTime;

    /**
     * 课程结束时间
     */
    private Date courseEndTime;

    /**
     * 课程校区
     */
    private String courseCampus;

    /**
     * 课程老师
     */
    private String courseTeacher;

    /**
     * 满级是否已满 0代表未满 1代表已满
     */
    private Integer courseFull;


}
