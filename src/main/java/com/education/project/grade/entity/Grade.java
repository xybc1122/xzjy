package com.education.project.grade.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2020-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 年级id
     */
    private Integer id;
    /**
     * 年级名称
     */
    private String gradeName;


}
