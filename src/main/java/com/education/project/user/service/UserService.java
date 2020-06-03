package com.education.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.education.project.base.HttpResult;
import com.education.project.user.entity.User;

public interface UserService extends IService<User> {

    HttpResult<User> wxLoginService(String userName, String password);

}
