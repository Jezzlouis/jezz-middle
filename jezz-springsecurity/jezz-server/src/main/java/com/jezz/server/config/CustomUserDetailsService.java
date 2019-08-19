package com.jezz.server.config;

import com.jezz.server.domain.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!Objects.equals("lee", s)) {
            throw new UsernameNotFoundException(s);
        }
        UserDetails userDetails = new CustomUser(s, passwordEncoder.encode("123456"));
        return userDetails;
    }
}
