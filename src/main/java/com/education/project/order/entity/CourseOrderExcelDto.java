package com.education.project.order.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class CourseOrderExcelDto {

    /**
     * 订单编号
     */
    @ExcelProperty(value = "订单号", index = 0)
    private String orderNumber;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", index = 1)
    private Date createTime;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格", index = 2)
    private BigDecimal price;



    /**
     * 是否支付0是未支付 1是已支付
     */
    @ExcelProperty(value = "是否支付", index = 3)
    private String statePay;


    /**
     * 表头说明
     */
    @ExcelProperty(value = "班级名称", index = 4)
    private String title;


    /**
     * 学生姓名
     */
    @ExcelProperty(value = "学生名称", index = 5)
    private String studentName;

}
