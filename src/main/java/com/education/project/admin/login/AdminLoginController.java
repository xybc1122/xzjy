package com.education.project.admin.login;

import com.education.project.admin.impl.AdminUserServiceImpl;
import com.education.project.base.HttpResult;
import com.education.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AdminLoginController {
    private final AdminUserServiceImpl adminUserService;

    @Autowired
    public AdminLoginController(AdminUserServiceImpl adminUserService) {
        this.adminUserService = adminUserService;
    }

    @RequestMapping("/so777")
    public String index() {

        return "login";
    }

    @PostMapping("/v1/api/admin/axiosLogin")
    @ResponseBody
    public HttpResult<User> adminLogin(@Validated @RequestBody User user, HttpServletResponse response) {
        return adminUserService.webLoginService(user.getUserName(), user.getPassword(), response);

    }
}
