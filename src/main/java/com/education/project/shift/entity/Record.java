package com.education.project.shift.entity;

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
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("shift_record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原班级id
     */
    private String oldCourseId;

    /**
     * 新班级id
     */
    private String newCourseId;

    /**
     * 调班时间
     */
    private Date createTime;

    /**
     * 原班级价格
     */
    private BigDecimal oldPrice;

    /**
     * 新班级价格
     */
    private BigDecimal newPrice;

    /**
     * 学员id
     */
    private String studentId;

    /**
     * 原课程名称
     */
    private String oldTitle;

    /**
     * 新课程名称
     */
    private String newTitle;


}
