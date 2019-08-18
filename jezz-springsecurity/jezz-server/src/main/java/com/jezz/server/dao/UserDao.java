package com.jezz.server.dao;

import com.jezz.server.entity.User;

public interface UserDao {
    User getUser(String username);
}
