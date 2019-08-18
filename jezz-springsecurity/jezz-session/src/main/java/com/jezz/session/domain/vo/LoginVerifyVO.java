package com.jezz.session.domain.vo;

import com.jezz.session.annotation.IsMobile;
import com.jezz.session.annotation.SpecifyIntValue;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginVerifyVO {
    @NotNull
    private String username;
    @NotNull
    @Length(min = 32,max = 32)
    private String password;
    @SpecifyIntValue(array = {1,2})
    private Integer mode = 1;
    @IsMobile
    private String phone;
    @Email
    private String email;
    private String verfyCode;
    // 标识国内外（1 国内 2国外）
    private Integer zone = 1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerfyCode() {
        return verfyCode;
    }

    public void setVerfyCode(String verfyCode) {
        this.verfyCode = verfyCode;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }
}
