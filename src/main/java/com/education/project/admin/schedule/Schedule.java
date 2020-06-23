package com.education.project.admin.schedule;

import com.education.project.course.entity.Info;
import com.education.project.course.service.IInfoService;
import com.education.project.exception.LsException;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.service.ICourseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Schedule {
    private final ICourseOrderService orderService;
    private final IInfoService courseInfoService;

    @Autowired
    public Schedule(ICourseOrderService orderService, IInfoService courseInfoService) {
        this.orderService = orderService;
        this.courseInfoService = courseInfoService;
    }

    /**
     * 后台定时执行过期订单同步 (部分订单会有5分钟延迟)
     */
    @Scheduled(fixedDelay = 20000)
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderState() {
        //查询要超时的订单
        List<CourseOrder> courseOrders = orderService.notPayOrderListService();
        if (courseOrders.isEmpty()) {
            return;
        }
        Map<String, Integer> courseOrderMap = new HashMap<>();
        courseOrders.forEach(item -> {
            courseOrderMap.merge(item.getCourseId(), 1, (a, b) -> a + b);
        });
        //还原课程数量
        for (Map.Entry<String, Integer> entry : courseOrderMap.entrySet()) {
            if (!courseInfoService.updateById(new Info(entry.getKey(), entry.getValue()))) {
                throw new LsException("err");
            }
        }
        if (orderService.updateOrderNotPayService() < 0) {
            throw new LsException("err");
        }
    }
}
