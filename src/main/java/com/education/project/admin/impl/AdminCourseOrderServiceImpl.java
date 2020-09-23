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

    @Autowired
    public AdminCourseOrderServiceImpl(IInfoService infoCourseService) {
        this.infoCourseService = infoCourseService;
    }

    @Override
    public HttpResult<Page<CourseOrder>> webGetCourseOrderListService(CourseOrderBo bo) {
        QueryWrapper<CourseOrder> query = new QueryWrapper<>();
        if (bo.getQueryTime() == null || bo.getQueryTime().isEmpty()) {
            return HttpResult.fail("请输入查询时间");
        }
        String start = bo.getQueryTime().get(0)+" 00:00:00";
        String end = bo.getQueryTime().get(1)+" 23:59:59";
        query.like(StringUtils.isNotEmpty(bo.getOrderNumber()),
                "order_number", bo.getOrderNumber())
                .ge("create_time", start)
                .le("create_time", end)
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
    public HttpResult webRemoveCourseOrderService(String orderNumber) {
        //获得订单信息
        CourseOrder order = getById(orderNumber);
        if (!removeById(orderNumber)) {
            return HttpResult.fail("删除失败");
        }
        //获得班级信息
        Info info = infoCourseService.getById(order.getCourseId());
        UpdateWrapper<Info> updateInfo = new UpdateWrapper<>();
        updateInfo.eq("course_id", order.getCourseId()).set("course_stock", info.getCourseStock() + 1);
        //如果这个课程已经满了 变成未满状态
        if (info.getCourseFull() == 1) {
            updateInfo.set("course_full", 0);
        }
        if (!infoCourseService.update(updateInfo)) {
            throw new LsException("删除失败");
        }
        return HttpResult.success("删除成功");
    }
}
