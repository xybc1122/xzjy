package com.education.project.text.entity;

import java.io.Serializable;
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
public class Text implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报名需知内容
     */
    private String textContent;


}
