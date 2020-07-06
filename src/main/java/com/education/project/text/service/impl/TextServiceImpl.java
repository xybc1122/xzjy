package com.education.project.text.service.impl;

import com.education.project.text.entity.Text;
import com.education.project.text.mapper.TextMapper;
import com.education.project.text.service.ITextService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-06
 */
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text> implements ITextService {

}
