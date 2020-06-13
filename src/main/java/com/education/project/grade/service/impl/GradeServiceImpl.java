package com.education.project.grade.service.impl;

import com.education.project.base.HttpResult;
import com.education.project.grade.entity.Grade;
import com.education.project.grade.mapper.GradeMapper;
import com.education.project.grade.service.IGradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-06-13
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements IGradeService {

    @Override
    public HttpResult<List<Grade>> listGrade() {
        return HttpResult.success(list());
    }
}
