package com.education.project.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.course.service.IInfoService;
import com.education.project.exception.LsException;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderVo;
import com.education.project.order.mapper.CourseOrderMapper;
import com.education.project.order.service.ICourseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class CourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder> implements ICourseOrderService {
    private final CourseOrderMapper orderMapper;

    private Lock lock = new ReentrantLock();

    private final IInfoService iInfoService;

    @Autowired
    public CourseOrderServiceImpl(IInfoService iInfoService, CourseOrderMapper orderMapper) {
        this.iInfoService = iInfoService;
        this.orderMapper = orderMapper;
    }

    @Override
    public HttpResult<String> getOrderCount(String studentId) {
        //已支付
        LambdaQueryWrapper<CourseOrder> payLambda = new LambdaQueryWrapper<>();
        payLambda.eq(CourseOrder::getStudentId, studentId).eq(CourseOrder::getStatePay, 1).eq(CourseOrder::getStateDel, 1);
        int payCount = count(payLambda);
        //未支付
        LambdaQueryWrapper<CourseOrder> notPayLambda = new LambdaQueryWrapper<>();
        notPayLambda.eq(CourseOrder::getStudentId, studentId).eq(CourseOrder::getStatePay, 0).eq(CourseOrder::getStateDel, 1);
        int notPayCount = count(notPayLambda);

        String strCount = payCount + "#" + notPayCount;

        return HttpResult.success(strCount, "success");
    }

    @Override
    public HttpResult<Page<CourseOrder>> getOrderList(String studentId, int isPay, int current, int offset) {
        LambdaQueryWrapper<CourseOrder> lambda = new LambdaQueryWrapper<>();
        lambda.eq(CourseOrder::getStatePay, isPay).eq(CourseOrder::getStudentId, studentId).
                eq(CourseOrder::getStateDel, 1).orderByDesc(CourseOrder::getCreateTime);
        Page<CourseOrder> page = page(new Page<>(current, offset), lambda);
        return HttpResult.success(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResult delOrder(String orderNumber) {
        UpdateWrapper<CourseOrder> updateCourseOrder = new UpdateWrapper<>();
        updateCourseOrder.eq("order_number", orderNumber).set("is_del", 0);
        if (!update(updateCourseOrder)) {
            return HttpResult.fail("删除失败");
        }
        lock.lock();
        try {
            //获得订单信息
            CourseOrder order = getById(orderNumber);
            //获得班级信息
            Info info = iInfoService.getById(order.getCourseId());
            UpdateWrapper<Info> updateInfo = new UpdateWrapper<>();
            updateInfo.eq("course_id", order.getCourseId()).set("course_stock", info.getCourseStock() + 1);
            //如果这个课程已经满了 变成未满状态
            if (info.getCourseFull() == 1) {
                updateInfo.set("course_full", 0);
            }
            if (!iInfoService.update(updateInfo)) {
                throw new LsException("删除失败");
            }
            return HttpResult.success("删除成功");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public HttpResult<CourseOrderVo> orderCreateTimeService(String orderNumber, String studentId) {
        QueryWrapper<CourseOrder> query = new QueryWrapper<>();
        query.eq("order_number", orderNumber).eq("student_id", studentId);
        CourseOrderVo coVo = new CourseOrderVo();
        coVo.setCreateTime(getOne(query).getCreateTime());
        return HttpResult.success(coVo);
    }

    @Override
    public List<CourseOrder> notPayOrderListService() {
        return orderMapper.notPayOrderList();
    }

    @Override
    public int updateOrderNotPayService() {
        return orderMapper.updateOrderNotPay();
    }
}
