package com.education.project.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.base.HttpResult;
import com.education.project.user.mapper.UserMapper;
import com.education.project.user.entity.User;
import com.education.project.user.service.UserService;
import com.education.project.utils.Base64Util;
import com.education.project.utils.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    public HttpResult<User> wxLoginService(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName).eq("tenant", 0);
        User user = getOne(queryWrapper);
        if (user == null) {
            return HttpResult.fail("账号或密码错误");
        }
        password = DigestUtils.md5DigestAsHex(Base64Util.
                encoder(password + "=#=" + user.getUserName()).getBytes());
        if (!user.getPassword().equals(password)) {
            return HttpResult.fail("账号或密码错误");
        }
        String token = JwtUtils.genJsonWebToken(user);
        user.setToken(token);
        return HttpResult.success(user);
    }

    @Override
    public User getUserInfo(String studentId) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("student_id", studentId);
        return getOne(query);
    }

}
