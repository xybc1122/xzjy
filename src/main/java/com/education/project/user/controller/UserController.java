package com.education.project.user.controller;

import com.education.project.base.HttpResult;
import com.education.project.user.service.impl.UserServiceImpl;
import com.education.project.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {


    private final UserServiceImpl userServiceImpl;


    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("wxLogin")
    public HttpResult<User> wxLogin(@Validated @RequestBody User user) {

        return userServiceImpl.wxLoginService(user.getStudentId(), user.getPassword());
    }
}
