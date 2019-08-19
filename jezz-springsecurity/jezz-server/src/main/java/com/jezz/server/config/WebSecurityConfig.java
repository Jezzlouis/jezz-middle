package com.jezz.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/security/test")
                .authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/login")
                .usernameParameter("account")
                .loginPage("/login.html")
                .and()
                .csrf()
                .disable();

    }
    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
