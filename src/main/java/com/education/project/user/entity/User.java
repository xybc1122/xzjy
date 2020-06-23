package com.education.project.user.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.project.base.BaseEntity;
import com.education.project.validation.ValidationGroupsUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User extends BaseEntity {

    @TableId
    private String studentId;

    @NotBlank(groups = {ValidationGroupsUser.Register.class}, message = "账号不能为空")
    @NotBlank(groups = {ValidationGroupsUser.WxEdit.class}, message = "账号不能为空")
    @Size(groups = {ValidationGroupsUser.WxEdit.class}, min = 2, max = 10,message = "账号不规范")
    @NotBlank(message = "账号不能为空")
    @Size(min = 2, max = 10,message = "账号不规范")
    private String userName;

    @NotBlank(groups = {ValidationGroupsUser.Register.class}, message = "密码不能为空")
    @NotBlank(groups = {ValidationGroupsUser.WxEdit.class}, message = "密码不能为空")
    @Size(groups = {ValidationGroupsUser.WxEdit.class}, min = 6, max = 15,message = "密码不规范")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 15,message = "密码不规范")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(groups = {ValidationGroupsUser.WxEdit.class}, message = "确认密码不能为空")
    @Size(groups = {ValidationGroupsUser.WxEdit.class}, min = 6, max = 15,message = "确认密码不规范")
    @TableField(exist = false)
    private String newPassword;
    /**
     * 姓名
     */
    @NotBlank(groups = {ValidationGroupsUser.Register.class}, message = "姓名不能为空")
    private String name;
    /**
     * 班级id
     */
    @NotNull(groups = {ValidationGroupsUser.Register.class}, message = "班级不能为空")
    private Integer gradeId;
    /**
     * 班级名称
     */
    private String gradeName;
    /**
     * 令牌
     */
    @TableField(exist = false)
    private String token;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer tenant;
    /**
     * 手机号码
     */
    @NotBlank(groups = {ValidationGroupsUser.Register.class}, message = "手机号码不能为空")
    private String phone;

    public User() {
    }

    public User(String studentId, String password) {
        this.studentId = studentId;
        this.password = password;
    }
}
