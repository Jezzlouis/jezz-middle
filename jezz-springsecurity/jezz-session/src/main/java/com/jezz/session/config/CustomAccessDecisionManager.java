package com.jezz.session.config;

import com.jezz.session.domain.po.TbPermissionPO;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CustomAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(n -> n.getAuthority().equals("ROLE_ANONYMOUS"))){
            throw new AccessDeniedException("no right");
        }
        for (ConfigAttribute configAttribute : collection) {
            List<TbPermissionPO> permissionsLogin = (List<TbPermissionPO>) authentication.getAuthorities();
            for (TbPermissionPO permissionPO : permissionsLogin) {
                String auth = permissionPO.getAuthority();
                if(configAttribute.getAttribute().trim().equals(auth)) {
                    return;
                }
            }
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
