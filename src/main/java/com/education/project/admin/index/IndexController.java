package com.education.project.admin.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/admin")
    public String index() {

        return "index";
    }


}
