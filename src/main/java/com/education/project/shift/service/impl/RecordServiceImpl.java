package com.education.project.shift.service.impl;

import com.education.project.shift.entity.Record;
import com.education.project.shift.mapper.RecordMapper;
import com.education.project.shift.service.IRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-07-06
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Override
    @Async("executor")
    public void executeAsyncInsert(Record record) {
        save(record);
    }
}
