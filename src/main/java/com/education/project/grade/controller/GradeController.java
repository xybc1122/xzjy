package com.education.project.grade.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.grade.entity.Grade;
import com.education.project.grade.service.IGradeService;
import com.education.project.order.entity.CourseOrder;
import com.education.project.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-13
 */
@RestController
@RequestMapping("/v1/api/grade")
public class GradeController {
    private final IGradeService gradeService;


    @Autowired
    public GradeController(IGradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("gradeList")
    public HttpResult<List<Grade>> gradeList() {
        return gradeService.listGrade();
    }
}
