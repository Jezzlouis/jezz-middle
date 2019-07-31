package com.jezz.dymdatasource.mapper;

import com.jezz.dymdatasource.entity.TbTaskDTO;

public interface TbTaskMapper {
    TbTaskDTO selectByTaskId(TbTaskDTO tbTaskDTO);
}
