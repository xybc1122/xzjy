package com.education.project.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;

public interface AdminCourseService extends IService<Info> {


    /**
     * 学生列表
     *
     * @param info
     * @return HttpResult
     */
    HttpResult<Page<Info>> webGetCourseListService(Info info);

    /**
     * 添加课程
     *
     * @param info
     * @return HttpResult
     */
    HttpResult webAddCourseService(Info info);
}
