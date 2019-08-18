package com.jezz.session.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        UserDetails userDetails;
        try
        {
            userDetails = customUserDetailsService.loadUserByUsername(s);
        }
        catch (UsernameNotFoundException notFound)
        {
            logger.error(notFound.getMessage());
            throw new BadCredentialsException(notFound.getMessage());
        }
        catch (Exception repositoryProblem)
        {
            logger.error(repositoryProblem);
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }
        if (userDetails == null)
        {
            throw new AuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        }
        // 此处校验密码
        if (token.getCredentials() == null)
        {
            throw new BadCredentialsException("用户账号密码错误");
        }
        String presentedPassword = token.getCredentials().toString();
        String encryptPassword = DigestUtils.md5Hex(presentedPassword);
        if (!userDetails.getPassword().equals(encryptPassword))
        {
            throw new BadCredentialsException("账号密码错误");
        }
        if(!userDetails.isAccountNonLocked()) {
            throw new AuthenticationServiceException("账号被锁定");
        }
        token.setDetails(userDetails);
        return userDetails;
    }
}
