package com.education.project.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.admin.service.AdminCourseOrderService;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.mapper.CourseOrderMapper;
import org.springframework.stereotype.Service;

@Service
public class AdminCourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder>
        implements AdminCourseOrderService {


    @Override
    public HttpResult<Page<CourseOrder>> webGetCourseOrderListService(CourseOrder courseOrder) {
        QueryWrapper<CourseOrder> query = new QueryWrapper<>();
        Page<CourseOrder> page = page(new Page<>(courseOrder.getCurrent(), courseOrder.getOffset()), query);
        return HttpResult.success(page);
    }
}
