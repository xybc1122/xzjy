package com.education.project.admin.user;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.admin.service.AdminUserService;
import com.education.project.base.HttpResult;
import com.education.project.order.entity.CourseOrder;
import com.education.project.user.entity.User;
import com.education.project.utils.RequestUtils;
import com.education.project.validation.ValidationGroupsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/v1/api/admin/getUserList")
    @ResponseBody
    public HttpResult<Page<User>> getUserList(@RequestBody User user) {

        return userService.webGetUserListService(user);

    }

    @PostMapping("/v1/api/admin/editUser")
    @ResponseBody
    public HttpResult editUser(@RequestBody User user) {
        return userService.webEditUserService(user);
    }


    @GetMapping("/v1/api/admin/removeUser")
    @ResponseBody
    public HttpResult removeUser(@RequestParam("studentId") String studentId) {
        return userService.webRemoveUserService(studentId);
    }

    @GetMapping("/v1/api/admin/resetPwd")
    @ResponseBody
    public HttpResult resetUserPassword(@RequestParam("studentId") String studentId) {
        return userService.webResetUserPasswordService(studentId);
    }


    @GetMapping("/v1/api/admin/editAdminPwd")
    @ResponseBody
    public HttpResult editAdminPwd(HttpServletRequest request, HttpServletResponse response, @RequestParam("pwd") String pwd) {
        String studentId = RequestUtils.getStudentId(request);
        return userService.webEditAdminPwdService(studentId, pwd, request, response);
    }
}
