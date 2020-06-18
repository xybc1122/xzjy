package com.education.project.order.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.education.project.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CourseOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @TableId
    private String orderNumber;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy/MM/dd", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 图片url
     */
    private String courseUrl;

    /**
     * 是否支付0是未支付 1是已支付
     */
    private Integer statePay;

    /**
     * 用户id
     */
    private String studentId;

    /**
     * 表头说明
     */
    private String title;

    /**
     * 微信小程序openId
     */
    private String openId;
    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 班级信息id
     */
    private String courseId;

    /**
     * 用户是否删除
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @TableField("is_del")
    private Integer stateDel;

}
