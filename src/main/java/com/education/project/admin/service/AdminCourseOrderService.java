package com.education.project.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderBo;

public interface AdminCourseOrderService extends IService<CourseOrder> {


    /**
     * 订单明细列表
     *
     * @param bo
     * @return HttpResult
     */
    HttpResult<Page<CourseOrder>> webGetCourseOrderListService(CourseOrderBo bo);



    HttpResult webRemoveCourseOrderService(String orderNumber);

}
