package com.education.project.admin.user;


import com.education.project.admin.service.AdminUserService;
import com.education.project.base.HttpResult;
import com.education.project.user.entity.User;
import com.education.project.validation.ValidationGroupsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminUserController {
    private final AdminUserService userService;

    @Autowired
    public AdminUserController(AdminUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user")
    public String user() {

        return "user";
    }


    @PostMapping("/v1/api/admin/addUser")
    @ResponseBody
    public HttpResult addUser(@Validated(ValidationGroupsUser.Register.class) @RequestBody User user) {

        return userService.webAddUserService(user);

    }
}
