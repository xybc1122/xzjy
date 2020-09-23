package com.education.project.wx;

import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;

public interface WxPayService {


    HttpResult<WxPay> WxPayService(CourseOrder order);
}
