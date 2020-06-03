package com.education.project.gc.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *  年级跟课程关联实体
 * </p>
 *
 * @author jobob
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GradeCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年级id
     */
    private Integer gradeId;

    /**
     * 课程id
     */
    private String courseId;


}
