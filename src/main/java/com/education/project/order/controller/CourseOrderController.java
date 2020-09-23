package com.education.project.order.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.config.WeChatConfig;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderVo;
import com.education.project.order.service.impl.CourseOrderServiceImpl;
import com.education.project.utils.RequestUtils;
import com.education.project.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

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
    @Autowired
    private WeChatConfig weChatConfig;

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


    @GetMapping("orderCreateTime")
    public HttpResult<CourseOrderVo> getOneOrderInfo(HttpServletRequest request, @RequestParam("orderNumber") String orderNumber) {
        String studentId = RequestUtils.getStudentId(request);
        return courseOrderService.orderCreateTimeService(orderNumber, studentId);
    }


    @GetMapping("addCourseOrder")
    public HttpResult addCourseOrder(HttpServletRequest request,
                                     @RequestParam("courseId") String courseId, @RequestParam("openId") String openId) {
        return courseOrderService.addCourseOrderService(courseId, RequestUtils.getStudentId(request), openId);
    }

    @GetMapping("modifyShift")
    public HttpResult modifyShift(HttpServletRequest request, @RequestParam("orderNumber") String orderNumber, @RequestParam("courseId") String courseId) {
        String studentId = RequestUtils.getStudentId(request);
        return courseOrderService.upShiftOrder(orderNumber, courseId, studentId);
    }


    /**
     * 微信支付回调
     */
    @GetMapping("callback")
    public void orderCallback(HttpServletRequest request,HttpServletResponse response) throws Exception {
        try (InputStream inputStream = request.getInputStream(); BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            //BufferedReader是包装设计模式，性能更搞
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            Map<String, String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
            System.out.println(callbackMap.toString());
            SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);
            //判断签名是否正确
            if (WXPayUtil.isCorrectSign(sortedMap, weChatConfig.getKey())) {
                if ("SUCCESS".equals(sortedMap.get("result_code"))) {
                    //获得订单号
                    String outTradeNo = sortedMap.get("out_trade_no");
                }
            }
        }
    }
}
