package com.education.project.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;

public interface AdminCourseService extends IService<Info> {


    /**
     * 课程班级列表
     *
     * @param info
     * @return HttpResult
     */
    HttpResult<Page<Info>> webGetCourseListService(Info info);

    /**
     * 添加课程班级
     *
     * @param info
     * @return HttpResult
     */
    HttpResult webAddCourseService(Info info);

    /**
     * 删除课程班级
     *
     * @param courseId 课程班级id
     * @return HttpResult
     */
    HttpResult webRemoveCourseService(String courseId);


    /**
     * 修改课程班级
     *
     * @param info
     * @return HttpResult
     */
    HttpResult webEditCourseService(Info info);
}
