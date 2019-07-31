package com.jezz.dymdatasource.service.test1.impl;

import com.jezz.dymdatasource.config.DataBase;
import com.jezz.dymdatasource.config.DataSourceType;
import com.jezz.dymdatasource.entity.TbTaskDTO;
import com.jezz.dymdatasource.mapper.TbTaskMapper;
import com.jezz.dymdatasource.service.test1.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TbTaskMapper tbTaskMapper;


    @Override
    @DataBase(value = DataSourceType.DATA_SOURCE_2)
    public TbTaskDTO queryByTaskId(TbTaskDTO tbTaskDTO) {
        return tbTaskMapper.selectByTaskId(tbTaskDTO);
    }
}
