package com.education.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.project.user.mapper.UserMapper;
import com.education.project.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProjectApplicationTests {


    @Autowired
    private UserMapper userMapper;


    @Test
    void contextLoads() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","婷婷");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

}
