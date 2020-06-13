package com.education.project.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.admin.service.AdminUserService;
import com.education.project.base.HttpResult;
import com.education.project.user.entity.User;
import com.education.project.user.mapper.UserMapper;
import com.education.project.utils.CookieUtil;
import com.education.project.utils.JwtUtils;
import com.education.project.utils.UUidUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;


@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {


    @Override
    public HttpResult<User> webLoginService(String userName, String password, HttpServletResponse response) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName).eq("tenant", 1);
        User user = getOne(queryWrapper);
        if (user == null) {
            return HttpResult.fail("账号或密码错误");
        }
        if (!user.getPassword().equals(password)) {
            return HttpResult.fail("账号或密码错误");
        }
        String token = JwtUtils.genJsonWebToken(user);
        CookieUtil.set(response, "token", token, true);
        return HttpResult.success(user);


    }

    @Override
    public HttpResult webAddUserService(User user) {
        user.setStudentId(UUidUtil.getUUid());
        if (!save(user)) {
            return HttpResult.fail("注册失败");
        }
        return HttpResult.success("注册成功");
    }
}
