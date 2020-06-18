package com.education.project.admin.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.admin.service.AdminCourseOrderService;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderBo;
import com.education.project.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminOrderController {
    private final AdminCourseOrderService courseOrderService;

    @Autowired
    public AdminOrderController(AdminCourseOrderService courseOrderService) {
        this.courseOrderService = courseOrderService;
    }

    @RequestMapping("/order")
    public String order() {

        return "order";
    }


    @PostMapping("/v1/api/admin/getCourseOrderList")
    @ResponseBody
    public HttpResult<Page<CourseOrder>> getCourseOrderList(@RequestBody CourseOrderBo bo) {
        return courseOrderService.webGetCourseOrderListService(bo);
    }


    @GetMapping("/v1/api/addCourseOrder")
    @ResponseBody
    public HttpResult addCourseOrder(HttpServletRequest request,
                                     @RequestParam("courseId") String courseId, @RequestParam("openId") String openId) {
        return courseOrderService.wxAddCourseOrderService(courseId, RequestUtils.getStudentId(request),openId);
    }
}
