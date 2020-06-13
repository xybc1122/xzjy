package com.education.project.grade.service;

import com.education.project.base.HttpResult;
import com.education.project.grade.entity.Grade;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-13
 */
public interface IGradeService extends IService<Grade> {

    /**
     * 获得 年级列表
     * @return HttpResult
     */
    HttpResult<List<Grade>> listGrade();

}
