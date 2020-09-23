package com.education.project.admin.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.admin.service.AdminCourseOrderService;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.order.entity.CourseOrderBo;
import com.education.project.order.entity.CourseOrderExcelDto;
import com.education.project.utils.EasyexcelUtil;
import com.education.project.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("/v1/api/admin/removeCourseOrder")
    @ResponseBody
    public HttpResult removeCourseOrder(@RequestParam("orderNumber") String orderNumber) {
        return courseOrderService.webRemoveCourseOrderService(orderNumber);
    }

    @PostMapping("/v1/api/admin/downloadOrderExcel")
    public void downloadOrderExcel(HttpServletResponse response, @RequestBody CourseOrderBo bo) throws IOException {
        bo.setOffset(Integer.MAX_VALUE);
        List<CourseOrder> records = courseOrderService.webGetCourseOrderListService(bo).getData().getRecords();
        List<CourseOrderExcelDto> excelDtoList = new ArrayList<>(records.size());
        records.forEach(item -> {
            CourseOrderExcelDto excelDto = new CourseOrderExcelDto();
            excelDto.setOrderNumber(item.getOrderNumber());
            excelDto.setCreateTime(item.getCreateTime());
            excelDto.setPrice(item.getPrice());
            excelDto.setStudentName(item.getStudentName());
            excelDto.setTitle(item.getTitle());
            excelDto.setStatePay(item.getStatePay() == 1 ? "已支付" :"未支付");
            excelDtoList.add(excelDto);
        });
        EasyexcelUtil.write(response, CourseOrderExcelDto.class, "订单统计", "订单统计", excelDtoList);
    }
}
