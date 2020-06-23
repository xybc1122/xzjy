package com.education.project.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.education.project.order.entity.CourseOrderVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
public interface ICourseOrderService extends IService<CourseOrder> {

    /**
     * 查询订单支付总数 跟未支付总数
     *
     * @return HttpResult
     */
    HttpResult<String> getOrderCount(String studentId);


    HttpResult<Page<CourseOrder>> getOrderList(String studentId, int isPay, int current, int offset);

    HttpResult delOrder(String orderNumber);


    HttpResult<CourseOrderVo> orderCreateTimeService(String orderNumber, String studentId);



    List<CourseOrder> notPayOrderListService();

    int updateOrderNotPayService();
}
