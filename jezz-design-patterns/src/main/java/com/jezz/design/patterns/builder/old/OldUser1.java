package com.jezz.design.patterns.builder.old;

/**
 * 传统实体类(有缺陷不使用)
 */
public class OldUser1 {
    private String userId; // require
    private String userName; // require
    private String password;//option
    private Integer age;//option
    private String address;//option
    private String email;//option
    private String phone;//option

    public OldUser1(String userId) {
        this.userId = userId;
    }

    public OldUser1(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public OldUser1(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public OldUser1(String userId, String userName, String password, Integer age) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

    public OldUser1(String userId, String userName, String password, Integer age, String address) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.address = address;
    }

    public OldUser1(String userId, String userName, String password, Integer age, String address, String email) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.address = address;
        this.email = email;
    }

    public OldUser1(String userId, String userName, String password, Integer age, String address, String email, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }
}
