package com.education.project.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.admin.service.AdminCourseOrderService;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderBo;
import com.education.project.order.mapper.CourseOrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AdminCourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder>
        implements AdminCourseOrderService {


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
}
