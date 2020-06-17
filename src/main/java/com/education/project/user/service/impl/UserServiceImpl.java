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
        User user = selUser(userName);
        if (user == null) {
            return HttpResult.fail("账号或密码错误");
        }
        if (!user.getPassword().equals(retPassword(password, user.getUserName()))) {
            return HttpResult.fail("账号或密码错误");
        }
        String token = JwtUtils.genJsonWebToken(user);
        user.setToken(token);
        return HttpResult.success(user);
    }

    @Override
    public HttpResult wxEditService(String userName, String oldPwd, String newPwd) {
        User user = selUser(userName);
        if (user == null) {
            return HttpResult.fail("账号或密码错误");
        }
        if (!user.getPassword().equals(retPassword(oldPwd, user.getUserName()))) {
            return HttpResult.fail("账号或密码错误");
        }
        //更新用户密码
        if (!updateById(new User(user.getStudentId(), retPassword(newPwd, user.getUserName())))) {
            return HttpResult.fail("修改失败");
        }
        return HttpResult.success("修改成功");
    }

    @Override
    public User getUserInfo(String studentId) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("student_id", studentId);
        return getOne(query);
    }

    private User selUser(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName).eq("tenant", 0);
        return getOne(queryWrapper);
    }

    private String retPassword(String password, String userName) {
        return DigestUtils.md5DigestAsHex(Base64Util.
                encoder(password + "=#=" + userName).getBytes());
    }
}
