package com.jezz.design.patterns.builder.telescoping;

public class User {
    private final String userId; // require
    private final String userName; // require
    private final String password;//option
    private final Integer age;//option
    private final String address;//option
    private final String email;//option
    private final String phone;//option

    private User(Builder builder) {
        userId = builder.userId;
        userName = builder.userName;
        password = builder.password;
        age = builder.age;
        address = builder.address;
        email = builder.email;
        phone = builder.phone;
    }


    public static final class Builder {
        private String userId;
        private String userName;
        private String password;
        private Integer age;
        private String address;
        private String email;
        private String phone;

        public Builder() {
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder userName(String val) {
            userName = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder age(Integer val) {
            age = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
