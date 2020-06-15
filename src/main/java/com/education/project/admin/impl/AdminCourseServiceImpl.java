package com.education.project.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.admin.service.AdminCourseService;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.course.mapper.InfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AdminCourseServiceImpl extends ServiceImpl<InfoMapper, Info> implements AdminCourseService {


    @Override
    public HttpResult<Page<Info>> webGetCourseListService(Info info) {
        QueryWrapper<Info> query = new QueryWrapper<>();
        query.eq(StringUtils.isNotEmpty(info.getCourseTitle()), "course_title", info.getCourseTitle());
        Page<Info> page = page(new Page<>(info.getCurrent(), info.getOffset()), query);
        return HttpResult.success(page);
    }

    @Override
    public HttpResult webAddCourseService(Info info) {
        

        return null;
    }
}
