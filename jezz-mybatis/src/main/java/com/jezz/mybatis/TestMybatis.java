package com.jezz.mybatis;

import com.jezz.bean.User;
import com.jezz.mapper.UserMapper;
import com.jezz.sqlsession.MySqlsession;

public class TestMybatis {
    public static void main(String[] args) {
        MySqlsession sqlsession=new MySqlsession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}
