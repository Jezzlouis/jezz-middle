package com.jezz.dymdatasource.entity;

import lombok.Data;

public class TbUserDTO {
    private Integer userId;
    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TbUserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
