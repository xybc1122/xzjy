package com.education.project.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.base.StatusCode;
import com.education.project.course.entity.Info;
import com.education.project.course.service.IInfoService;
import com.education.project.exception.LsException;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderBo;
import com.education.project.order.entity.CourseOrderVo;
import com.education.project.order.mapper.CourseOrderMapper;
import com.education.project.order.service.ICourseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.shift.entity.Record;
import com.education.project.shift.service.IRecordService;
import com.education.project.user.entity.User;
import com.education.project.user.service.UserService;
import com.education.project.utils.RecordNoUtils;
import com.education.project.wx.WxPay;
import com.education.project.wx.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    private IInfoService iInfoService;
    @Autowired
    private IRecordService recordService;
    @Autowired
    private IInfoService infoCourseService;
    @Autowired
    private UserService userService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    public CourseOrderServiceImpl(CourseOrderMapper orderMapper) {
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
            //课程数量 ++
            updateInfo(info.getCourseId(), info.getCourseStock() + 1, info.getCourseFull());
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

    @Override
    public List<CourseOrder> getOrderByStudentList(String studentId) {
        QueryWrapper<CourseOrder> query = new QueryWrapper<>();
        query.eq("student_id", studentId).eq("is_del", 1);
        return list(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResult upShiftOrder(String orderNumber, String courseId, String studentId) {
        try {
            lock.lock();
            //获得新的班级信息
            Info newInfo = iInfoService.getById(courseId);
            Integer courseStock = newInfo.getCourseStock();
            //如果是0说明班级已经满了
            if (courseStock == 0) {
                return HttpResult.fail("该班级已经满,不能在调班");
            }

            //获得订单信息
            CourseOrder order = getById(orderNumber);
            //查询原来课程的班级信息
            Info oldInfo = iInfoService.getById(order.getCourseId());
            //++ 原课程数量
            updateInfo(order.getCourseId(), oldInfo.getCourseStock() + 1, oldInfo.getCourseFull());

            //-- 新班级数量
            updateInfo(newInfo.getCourseId(), newInfo.getCourseStock() - 1, 2);


            //更新订单信息
            UpdateWrapper<CourseOrder> updateOrder = new UpdateWrapper<>();
            updateOrder.set("course_url", newInfo.getCourseUrl()).
                    set("title", newInfo.getCourseTitle()).
                    set("course_id", newInfo.getCourseId()).
                    set("price", newInfo.getCoursePrice()).
                    eq("is_del", 1).eq("state_pay", 1).
                    eq("order_number", orderNumber);
            if (!update(updateOrder)) {
                return HttpResult.fail(StatusCode.FAILURE.getMessage());
            }
            Record record = new Record();
            record.setOldPrice(oldInfo.getCoursePrice());
            record.setOldCourseId(oldInfo.getCourseId());
            record.setOldTitle(oldInfo.getCourseTitle());
            record.setNewPrice(newInfo.getCoursePrice());
            record.setNewCourseId(newInfo.getCourseId());
            record.setNewTitle(newInfo.getCourseTitle());
            record.setCreateTime(new Date());
            record.setStudentId(studentId);
            recordService.executeAsyncInsert(record);
            return HttpResult.success(StatusCode.SUCCESS.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResult addCourseOrderService(String courseId, String studentId, String openId) {
        try {
            lock.lock();
            QueryWrapper<CourseOrder> coQuery = new QueryWrapper<>();
            coQuery.eq("course_id", courseId).eq("student_id", studentId).eq("is_del", 1).last("LIMIT 1");
            CourseOrder co = getOne(coQuery);
            if (co != null) {
                return HttpResult.fail("您已经报名此班级课程,请去我的订单查看");
            }
            Info courseInfo = infoCourseService.getById(courseId);
            if (courseInfo.getCourseStock() <= 0) {
                return HttpResult.fail("班级人数已满");
            }
            User userInfo = userService.getUserInfo(studentId);
            CourseOrder courseOrder = new CourseOrderBo();
            courseOrder.setStudentName(userInfo.getName());
            courseOrder.setCourseId(courseId);
            courseOrder.setOpenId(openId);
            courseOrder.setStudentId(studentId);
            courseOrder.setStatePay(0);
            courseOrder.setOrderNumber(RecordNoUtils.getInstance().getOrderNumber());
            courseOrder.setCreateTime(new Date());
            courseOrder.setPrice(courseInfo.getCoursePrice());
            courseOrder.setTitle(courseInfo.getCourseTitle());
            courseOrder.setCourseUrl(courseInfo.getCourseUrl());
            if (!save(courseOrder)) {
                return HttpResult.fail("报名失败");
            }
            UpdateWrapper<Info> updateInfo = new UpdateWrapper<>();
            int stock = courseInfo.getCourseStock() - 1;
            updateInfo.eq("course_id", courseId).set("course_stock", stock);
            if (!infoCourseService.update(updateInfo)) {
                throw new LsException("报名失败");
            }
            //说明已经没有库存了
            if (stock == 0) {
                UpdateWrapper<Info> update = new UpdateWrapper<>();
                update.eq("course_id", courseId).set("course_full", 1);
                if (!infoCourseService.update(update)) {
                    throw new LsException("报名失败");
                }
            }
            //添加订单成功开始调用支付
            wxPayService.WxPayService(courseOrder);
            return HttpResult.success("报名成功");
        } finally {
            lock.unlock();
        }
    }

    private void updateInfo(String courseId, Integer courseStock, Integer courseFull) {
        UpdateWrapper<Info> updateInfo = new UpdateWrapper<>();
        updateInfo.eq("course_id", courseId).set("course_stock", courseStock);
        //如果这个课程已经满了 变成未满状态
        if (courseFull == 1) {
            updateInfo.set("course_full", 0);
        }
        //如果没有课程了
        if (courseStock == 0) {
            updateInfo.set("course_full", 1);
        }
        if (!iInfoService.update(updateInfo)) {
            throw new LsException(StatusCode.FAILURE.getMessage());
        }
    }
}
