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

    /**
     * 获取支付 or未支付的 订单集合
     *
     * @param studentId
     * @param isPay
     * @param current
     * @param offset
     * @return
     */
    HttpResult<Page<CourseOrder>> getOrderList(String studentId, int isPay, int current, int offset);

    /**
     * 伪删除 订单
     *
     * @param orderNumber
     * @return
     */
    HttpResult delOrder(String orderNumber);

    /**
     * 获得订单创建时间
     *
     * @param orderNumber
     * @param studentId
     * @return
     */
    HttpResult<CourseOrderVo> orderCreateTimeService(String orderNumber, String studentId);

    /**
     * 查询超时的订单
     *
     * @return
     */
    List<CourseOrder> notPayOrderListService();

    /**
     * 更新超时的订单
     *
     * @return
     */
    int updateOrderNotPayService();

    /**
     * 获得 用户已经有的班级订单
     *
     * @param studentId
     * @return
     */
    List<CourseOrder> getOrderByStudentList(String studentId);

    /**
     * 调班更新订单
     *
     * @param orderNumber 要更新的订单号
     * @param courseId    新的班级id
     * @return
     */
    HttpResult upShiftOrder(String orderNumber, String courseId,String studentId);



    /**
     * 添加订单
     *
     * @param courseId
     * @return HttpResult
     */
    HttpResult addCourseOrderService(String courseId, String studentId, String openId);

}
