package com.education.project.course.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.education.project.base.BaseEntity;
import com.education.project.validation.ValidationGroupsCourse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class Info extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程id
     */
    @TableId
    private String courseId;

    /**
     * 课程描述
     */
    @NotBlank(groups = {ValidationGroupsCourse.Register.class}, message = "班级描述不能为空")
    private String courseDesc;

    /**
     * 课程标题
     */
    @NotBlank(groups = {ValidationGroupsCourse.Register.class}, message = "班级标题不能为空")
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
    @JsonFormat(pattern = "yyyy/MM/dd", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date courseStartTime;

    /**
     * 课程结束时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date courseEndTime;

    /**
     * 课程校区
     */
    @NotBlank(groups = {ValidationGroupsCourse.Register.class}, message = "班级校区不能为空")
    private String courseCampus;

    /**
     * 课程老师
     */
    @NotBlank(groups = {ValidationGroupsCourse.Register.class}, message = "班级老师不能为空")
    private String courseTeacher;

    /**
     * 满级是否已满 0代表未满 1代表已满
     */
    private Integer courseFull;
    /**
     * 班级人数数量
     */
    @NotNull(groups = {ValidationGroupsCourse.Register.class}, message = "班级人数不能为空")
    private Integer courseStock;

}
