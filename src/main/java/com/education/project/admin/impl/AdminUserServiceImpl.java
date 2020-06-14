package com.education.project.admin.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.education.project.admin.service.AdminUserService;
import com.education.project.base.HttpResult;
import com.education.project.grade.entity.Grade;
import com.education.project.grade.service.IGradeService;
import com.education.project.user.entity.User;
import com.education.project.user.mapper.UserMapper;
import com.education.project.utils.CookieUtil;
import com.education.project.utils.JwtUtils;
import com.education.project.utils.UUidUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {
    private Lock lock = new ReentrantLock();
    private final IGradeService gradeService;

    @Autowired
    public AdminUserServiceImpl(IGradeService gradeService) {
        this.gradeService = gradeService;
    }

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
        try {
            lock.lock();
            QueryWrapper<User> queryUser = new QueryWrapper<>();
            queryUser.eq("user_name", user.getUserName());
            if (getOne(queryUser) != null) {
                return HttpResult.fail("账号已被注册");
            }
            user.setStudentId(UUidUtil.getUUid());
            Grade grade = getGrade(user.getGradeId());
            user.setGradeName(grade.getGradeName());
            if (!save(user)) {
                return HttpResult.fail("注册失败");
            }
            return HttpResult.success("注册成功");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public HttpResult webEditUserService(User user) {
        Grade grade = getGrade(user.getGradeId());
        user.setGradeName(grade.getGradeName());
        if (!updateById(user)) {
            return HttpResult.fail("修改失败");
        }
        return HttpResult.success("修改成功");
    }

    @Override
    public HttpResult webRemoveUserService(String studentId) {
        if (!removeById(studentId)) {
            return HttpResult.fail("删除失败");
        }
        return HttpResult.success("删除成功");
    }

    @Override
    public HttpResult<Page<User>> webGetUserListService(User user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("tenant", 0).
                eq(StringUtils.isNotEmpty(user.getName()), "name", user.getName());
        Page<User> page = page(new Page<>(user.getCurrent(), user.getOffset()), query);
        return HttpResult.success(page);
    }

    private Grade getGrade(Integer gradeId) {
        QueryWrapper<Grade> queryGrade = new QueryWrapper<>();
        queryGrade.eq("id", gradeId);
        return gradeService.getOne(queryGrade);
    }
}
