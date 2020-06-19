package com.education.project.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.admin.service.AdminCourseOrderService;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.course.service.IInfoService;
import com.education.project.exception.LsException;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderBo;
import com.education.project.order.mapper.CourseOrderMapper;
import com.education.project.user.entity.User;
import com.education.project.user.service.UserService;
import com.education.project.utils.RecordNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AdminCourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder>
        implements AdminCourseOrderService {
    private final IInfoService infoCourseService;
    private final UserService userService;
    private final IInfoService iInfoService;
    private Lock lock = new ReentrantLock();

    @Autowired
    public AdminCourseOrderServiceImpl(IInfoService infoCourseService, UserService userService, IInfoService iInfoService) {
        this.infoCourseService = infoCourseService;
        this.userService = userService;
        this.iInfoService = iInfoService;
    }

    @Override
    public HttpResult<Page<CourseOrder>> webGetCourseOrderListService(CourseOrderBo bo) {
        QueryWrapper<CourseOrder> query = new QueryWrapper<>();
        if (bo.getQueryTime() == null || bo.getQueryTime().isEmpty()) {
            return HttpResult.fail("请输入查询时间");
        }
        String start = bo.getQueryTime().get(0);
        String end = bo.getQueryTime().get(1);
        query.like(StringUtils.isNotEmpty(bo.getOrderNumber()),
                "order_number", bo.getOrderNumber())
                .between("create_time", start, end)
                .like(StringUtils.isNotEmpty(bo.getStudentName()),
                        "student_name", bo.getStudentName())
                .like(StringUtils.isNotEmpty(bo.getTitle()),
                        "title", bo.getTitle())
                .eq(StringUtils.isNotEmpty(bo.getPayState()), "state_pay",
                        bo.getPayState()).orderByDesc("create_time");
        Page<CourseOrder> page = page(new Page<>(bo.getCurrent(), bo.getOffset()), query);
        return HttpResult.success(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResult wxAddCourseOrderService(String courseId, String studentId, String openId) {
        try {
            lock.lock();
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
            return HttpResult.success("报名成功");
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpResult webRemoveCourseOrderService(String orderNumber) {
        //获得订单信息
        CourseOrder order = getById(orderNumber);
        if (!removeById(orderNumber)) {
            return HttpResult.fail("删除失败");
        }
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
    }
}
