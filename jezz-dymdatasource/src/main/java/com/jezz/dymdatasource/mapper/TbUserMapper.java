package com.jezz.dymdatasource.mapper;

import com.jezz.dymdatasource.entity.TbUserDTO;

public interface TbUserMapper {
    TbUserDTO selectByUserId(TbUserDTO tbUserDTO);
}
