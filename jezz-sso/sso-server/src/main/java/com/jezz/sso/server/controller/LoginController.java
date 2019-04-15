package com.jezz.sso.server.controller;

import com.jezz.sso.server.constants.ConstantKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class LoginController extends BaseController{

    @RequestMapping(value = "signup",method = RequestMethod.POST)
    public void sighup(HttpServletRequest request, HttpServletResponse response){

    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response){
        List roleList = new ArrayList<>();
        String subject = "admin" + "-" + roleList;
        String token = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000)) // 设置过期时间 365 * 24 * 60 * 60秒(这里为了方便测试，所以设置了1年的过期时间，实际项目需要根据自己的情况修改)
                .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY)
                .compact();
        // 登录成功后，返回token到header里面
        response.addHeader("auth-token", "Bearer " + token);
    }
    @RequestMapping(value = "list",method = RequestMethod.POST)
    public void getUserList(HttpServletRequest request, HttpServletResponse response){
        System.out.println(1);
    }

}
