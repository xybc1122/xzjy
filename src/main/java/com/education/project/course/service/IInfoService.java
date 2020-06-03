package com.education.project.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.project.base.HttpResult;
import com.education.project.course.entity.Info;
import com.baomidou.mybatisplus.extension.service.IService;
import com.education.project.course.entity.InfoVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-03
 */
public interface IInfoService extends IService<Info> {


    HttpResult<Page<Info>> getCourseList(String gradeId,Integer current,Integer offset);

}
