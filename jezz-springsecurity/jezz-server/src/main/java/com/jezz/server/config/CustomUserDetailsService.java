package com.jezz.server.config;

import com.jezz.server.domain.CustomUser;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomUserDetailsService implements UserDetailsService {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!Objects.equals("lee", s)) {
            throw new UsernameNotFoundException(s);
        }
        UserDetails userDetails = new CustomUser(s, DigestUtils.md5Hex("123456"));
        return userDetails;
    }
}
