package com.jezz.design.patterns.builder.telescoping;

public class BuildTest {
    public static void main(String[] args) {
        User user = new User.Builder()
                .userId("123").userName("jezz")
                .password("222")
                .address("深圳")
                .age(27)
                .email("123484589@qq.com")
                .phone("13178906789")
                .build();
        System.out.println(user);
    }
}
