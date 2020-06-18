package com.education.project.order.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.service.impl.CourseOrderServiceImpl;
import com.education.project.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@RestController
@RequestMapping("/v1/api/order/course-order")
public class CourseOrderController {
    private final CourseOrderServiceImpl courseOrderService;

    @Autowired
    public CourseOrderController(CourseOrderServiceImpl courseOrderService) {
        this.courseOrderService = courseOrderService;
    }

    @GetMapping("payCount")
    public HttpResult<String> payCount(HttpServletRequest request) {
        String studentId = RequestUtils.getStudentId(request);
        return courseOrderService.getOrderCount(studentId);
    }


    @GetMapping("orderList")
    public HttpResult<Page<CourseOrder>> orderList(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                   @RequestParam(value = "offset", defaultValue = "10") Integer offset,
                                                   @RequestParam("isPay") int isPay,
                                                   HttpServletRequest request) {
        String studentId = RequestUtils.getStudentId(request);
        return courseOrderService.getOrderList(studentId, isPay, current, offset);
    }


    @GetMapping("delOrder")
    public HttpResult delOrder(@RequestParam("orderNumber") String orderNumber) {
        return courseOrderService.delOrder(orderNumber);
    }

}
