package com.jezz;

import java.util.Optional;

public class OptionTest {
    public static void main(String[] args) {
        User user = new User("bbb");
        user = Optional.ofNullable(user).orElse(new User().createUser());
        //user = Optional.ofNullable(user).orElseGet(() -> new User().createUser());
        System.out.println(user.getName());
    }
}

class User{
    private String name;

    public User(){}

    public User(String name){
        this.name = name;
    }

    public User createUser(){
        User user = new User();
        user.setName("aaa");
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}