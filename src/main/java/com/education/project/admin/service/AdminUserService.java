package com.education.project.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.education.project.base.HttpResult;
import com.education.project.user.entity.User;

import javax.servlet.http.HttpServletResponse;

public interface AdminUserService extends IService<User> {
    /**
     * web 登陆
     * @param userName
     * @param password
     * @param response
     * @return
     */
    HttpResult<User> webLoginService(String userName, String password, HttpServletResponse response);

    /**
     * 添加学生
     * @param user
     * @return
     */
    HttpResult webAddUserService(User user);


    /**
     * 添加学生
     * @param user
     * @return
     */
    HttpResult<Page<User>> webGetUserListService(User user);
}
