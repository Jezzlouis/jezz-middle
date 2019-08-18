package com.jezz.server.dao.impl;

import com.jezz.server.dao.UserDao;
import com.jezz.server.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private static final Map<String, User> userMap = new HashMap<String, User>();
    static {
        User user = new User();
        user.setUsername("liyd");
        user.setPassword("123456");
        user.setRole("user");
        userMap.put(user.getUsername(), user);
        user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setRole("admin");
        userMap.put(user.getUsername(), user);
    }
    @Override
    public User getUser(String username) {
        return userMap.get(username);
    }
}