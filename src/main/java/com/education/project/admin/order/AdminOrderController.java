package com.education.project.admin.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.admin.service.AdminCourseOrderService;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
