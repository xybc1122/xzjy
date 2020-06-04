package com.education.project.course.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.course.service.impl.InfoServiceImpl;
import com.education.project.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/v1/api/course/")
public class InfoController {
    private final InfoServiceImpl infoService;

    @Autowired
    public InfoController(InfoServiceImpl infoService) {
        this.infoService = infoService;
    }


    @GetMapping("list")
    public HttpResult<Page<Info>> list(@RequestParam(value="current",defaultValue = "1")Integer current,
                                       @RequestParam(value="offset",defaultValue = "10")Integer offset, HttpServletRequest request) {
        Integer gradeId = RequestUtils.getGradeId(request);
        return infoService.getCourseList(gradeId, current, offset);
    }
}
