package com.education.project.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.admin.service.AdminCourseService;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.course.mapper.InfoMapper;
import com.education.project.utils.UUidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AdminCourseServiceImpl extends ServiceImpl<InfoMapper, Info> implements AdminCourseService {


    @Override
    public HttpResult<Page<Info>> webGetCourseListService(Info info) {
        QueryWrapper<Info> query = new QueryWrapper<>();
        query.like(StringUtils.isNotEmpty(info.getCourseTitle()), "course_title", info.getCourseTitle());
        Page<Info> page = page(new Page<>(info.getCurrent(), info.getOffset()), query);
        return HttpResult.success(page);
    }

    @Override
    public HttpResult webAddCourseService(Info info) {
        if (info.getCourseStock().equals(0)) {
            //设置班级已满
            info.setCourseFull(1);
        }
        info.setCourseId(UUidUtil.getUUid());
        if (!save(info)) {
            return HttpResult.fail("添加失败");
        }
        return HttpResult.success("添加成功");
    }

    @Override
    public HttpResult webRemoveCourseService(String courseId) {
        if (!removeById(courseId)) {
            return HttpResult.fail("删除失败");
        }
        return HttpResult.success("删除成功");
    }

    @Override
    public HttpResult webEditCourseService(Info info) {
        if (info.getCourseStock().equals(0)) {
            //设置班级已满
            info.setCourseFull(1);
        }
        if (!updateById(info)) {
            return HttpResult.fail("修改失败");
        }
        return HttpResult.success("修改成功");
    }
}
