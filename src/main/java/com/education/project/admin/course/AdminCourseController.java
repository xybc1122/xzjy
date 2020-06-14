package com.education.project.admin.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminCourseController {

    @RequestMapping("/course")
    public String course() {

        return "course";
    }

}
