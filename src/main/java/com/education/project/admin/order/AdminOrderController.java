package com.education.project.admin.order;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminOrderController {

    @RequestMapping("/order")
    public String order() {

        return "order";
    }
}
