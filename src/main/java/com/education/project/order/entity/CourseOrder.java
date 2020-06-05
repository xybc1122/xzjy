package com.education.project.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy/MM/dd",locale="zh",timezone="GMT+8")
    private Date createTime;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片url
     */
    private String url;

    /**
     * 是否支付0是未支付 1是已支付
     */
    private Integer isPay;

    /**
     * 用户id
     */
    private String studentId;


}
