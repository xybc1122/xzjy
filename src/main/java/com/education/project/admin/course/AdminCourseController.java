package com.education.project.admin.course;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.admin.service.AdminCourseService;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.user.entity.User;
import com.education.project.validation.ValidationGroupsCourse;
import com.education.project.validation.ValidationGroupsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminCourseController {

    private final AdminCourseService courseService;

    @Autowired
    public AdminCourseController(AdminCourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping("/course")
    public String course() {

        return "course";
    }


    @PostMapping("/v1/api/admin/getCourseList")
    @ResponseBody
    public HttpResult<Page<Info>> getCourseList(@RequestBody Info info) {
        return courseService.webGetCourseListService(info);
    }


    @PostMapping("/v1/api/admin/addCourse")
    @ResponseBody
    public HttpResult addCourse(@Validated(ValidationGroupsCourse.Register.class) @RequestBody Info info) {
        return null;
    }

}
