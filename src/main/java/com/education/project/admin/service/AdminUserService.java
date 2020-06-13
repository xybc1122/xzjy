package com.education.project.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.education.project.base.HttpResult;
import com.education.project.user.entity.User;

import javax.servlet.http.HttpServletResponse;

public interface AdminUserService extends IService<User> {

    HttpResult<User> webLoginService(String userName, String password, HttpServletResponse response);


    HttpResult webAddUserService(User user);
}
