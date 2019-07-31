package com.jezz.dymdatasource.service.test2.impl;

import com.jezz.dymdatasource.config.DataBase;
import com.jezz.dymdatasource.config.DataSourceType;
import com.jezz.dymdatasource.entity.TbUserDTO;
import com.jezz.dymdatasource.mapper.TbUserMapper;
import com.jezz.dymdatasource.service.test2.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    @DataBase(value = DataSourceType.DATA_SOURCE_1)
    public TbUserDTO queryByUserId(TbUserDTO tbUserDTO){
        return tbUserMapper.selectByUserId(tbUserDTO);
    }
}
