package com.education.project.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.course.mapper.InfoMapper;
import com.education.project.course.service.IInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.gc.entity.GradeCourse;
import com.education.project.gc.service.IGradeCourseService;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.service.ICourseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-03
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements IInfoService {
    private final IGradeCourseService courseService;

    @Autowired
    private ICourseOrderService orderService;

    @Autowired
    public InfoServiceImpl(IGradeCourseService courseService) {
        this.courseService = courseService;

    }


    @Override
    public HttpResult<Page<Info>> getCourseList(Integer gradeId, Integer current, Integer offset) {

        List<String> courseIds = getCourseIds(gradeId);

        if (courseIds.isEmpty()) {
            return HttpResult.success(new Page<>());
        }
        return HttpResult.success(page(courseIds, current, offset));
    }

    @Override
    public HttpResult<Page<Info>> getNotCourseIdList(String studentId, Integer gradeId, Integer current, Integer offset) {
        //查询已有的课程
        List<CourseOrder> orderByStudentList = orderService.getOrderByStudentList(studentId);
        if (orderByStudentList.isEmpty()) {
            return HttpResult.success(new Page<>());
        }
        List<String> courseIds = getCourseIds(gradeId);

        if (courseIds.isEmpty()) {
            return HttpResult.success(new Page<>());
        }
        for (CourseOrder c : orderByStudentList) {
            courseIds.removeIf(next -> c.getCourseId().equals(next));
        }

        if (courseIds.isEmpty()) {
            return HttpResult.success(new Page<>());
        }
        return HttpResult.success(page(courseIds, current, offset));
    }


    private List<String> getCourseIds(Integer gradeId) {
        QueryWrapper<GradeCourse> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("grade_id", gradeId);

        List<GradeCourse> listGradeCourse = courseService.list(queryWrapper);

        List<String> courseIds = new ArrayList<>(listGradeCourse.size());

        listGradeCourse.forEach(item -> courseIds.add(item.getCourseId()));

        return courseIds;
    }

    private Page<Info> page(List<String> courseIds, Integer current, Integer offset) {
        QueryWrapper<Info> voQueryWrapper = new QueryWrapper<>();
        voQueryWrapper.in("course_id", courseIds);
        voQueryWrapper.orderByDesc("course_start_time");
        return page(new Page<>(current, offset), voQueryWrapper);
    }
}
