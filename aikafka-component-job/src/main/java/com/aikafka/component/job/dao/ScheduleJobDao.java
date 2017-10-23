package com.aikafka.component.job.dao;

import com.aikafka.component.job.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO〈一句话类描述〉
 * 项目名称:咪咕合管
 * 包名称: com.aikafka.component.job.dao
 * 类名称: ScheduleJobDao
 * 类描述:
 * 创建人: zhuxiaolong@aspirecn.com
 * 创建时间:2017/10/22
 * 版本： V1.0.0
 */
@Mapper
public interface ScheduleJobDao {
    void insert(ScheduleJob job);

    int deleteByPrimaryKey(Long jobId);

    ScheduleJob selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);

    List<ScheduleJob> getAll();

    List<ScheduleJob> getTaskByContent(String content);
}
