package com.jezz.design.patterns.builder.old;

public class OldUser2 {
    private String userId; // require
    private String userName; // require
    private String password;//option
    private Integer age;//option
    private String address;//option
    private String email;//option
    private String phone;//option

    public OldUser2() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
