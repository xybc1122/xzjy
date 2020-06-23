package com.education.project.order.mapper;

import com.education.project.order.entity.CourseOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface CourseOrderMapper extends BaseMapper<CourseOrder> {


    @Select("SELECT`order_number`,`course_id`FROM `course_order`" +
            " WHERE UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(create_time) >1800 AND is_del=1 AND state_pay=0")
    List<CourseOrder> notPayOrderList();

    @Update("UPDATE `course_order` SET `is_del` = 0\n" +
            "WHERE UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(create_time) >1800 AND is_del=1 AND state_pay=0 ")
    int updateOrderNotPay();
}
