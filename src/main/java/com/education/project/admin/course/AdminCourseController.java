package com.education.project.admin.course;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.admin.service.AdminCourseService;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.validation.ValidationGroupsCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return courseService.webAddCourseService(info);
    }


    @GetMapping("/v1/api/admin/removeCourse")
    @ResponseBody
    public HttpResult removeCourse(@RequestParam("courseId") String courseId) {
        return courseService.webRemoveCourseService(courseId);
    }

    @PostMapping("/v1/api/admin/editCourse")
    @ResponseBody
    public HttpResult editCourse(@RequestBody Info info) {

        return courseService.webEditCourseService(info);
    }

}
