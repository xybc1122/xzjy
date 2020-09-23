package com.education.project.shift.service;

import com.education.project.shift.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-07-06
 */
public interface IRecordService extends IService<Record> {


    void executeAsyncInsert(Record record);

}
