package com.jezz.server.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义密码保护验证
 */
@Slf4j
public class CustomPasswordProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        UserDetails userDetails;
        try {
            userDetails = customUserDetailsService.loadUserByUsername(s);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException(e.getMessage());
        }
        if(userDetails == null){
            logger.error("userdetails is null");
        }
        String pwd = usernamePasswordAuthenticationToken.getCredentials().toString();
        String encryptPassword = DigestUtils.md5Hex(pwd);
        if (!userDetails.getPassword().equals(encryptPassword)){
            throw new BadCredentialsException("账号密码错误");
        }
        return userDetails;
    }
}
