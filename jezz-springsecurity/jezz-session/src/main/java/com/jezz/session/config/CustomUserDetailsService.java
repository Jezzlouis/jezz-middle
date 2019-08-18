package com.jezz.session.config;

import com.jezz.session.domain.dto.UserSubject;
import com.jezz.session.domain.po.TbAdminPO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TbAdminPO admin = new TbAdminPO();
        admin.setLoginName("test");
        admin.setLoginPassword("123");
        if (admin == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        return new UserSubject.Builder()
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .username(admin.getLoginName())
                .password(admin.getLoginPassword()).build();
    }
}
