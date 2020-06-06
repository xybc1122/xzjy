package com.education.project.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.mapper.CourseOrderMapper;
import com.education.project.order.service.ICourseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-05
 */
@Service
public class CourseOrderServiceImpl extends ServiceImpl<CourseOrderMapper, CourseOrder> implements ICourseOrderService {

    @Override
    public HttpResult<String> getOrderCount(String studentId) {
        //已支付
        LambdaQueryWrapper<CourseOrder> payLambda = new LambdaQueryWrapper<>();
        payLambda.eq(CourseOrder::getStudentId, studentId).eq(CourseOrder::getIsPay, 1);
        int payCount = count(payLambda);
        //未支付
        LambdaQueryWrapper<CourseOrder> notPayLambda = new LambdaQueryWrapper<>();
        notPayLambda.eq(CourseOrder::getStudentId, studentId).eq(CourseOrder::getIsPay, 0);
        int notPayCount = count(notPayLambda);

        String strCount = payCount + "#" + notPayCount;

        return HttpResult.success(strCount, "success");
}

    @Override
    public HttpResult<Page<CourseOrder>> getOrderList(String studentId, int isPay, int current, int offset) {
        LambdaQueryWrapper<CourseOrder> lambda = new LambdaQueryWrapper<>();
        lambda.eq(CourseOrder::getIsPay,isPay).eq(CourseOrder::getStudentId,studentId);
        lambda.orderByDesc(CourseOrder::getCreateTime);
        Page<CourseOrder> page =page(new Page<>(current,offset),lambda);
        return HttpResult.success(page);
    }
}
