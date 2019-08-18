package com.jezz.session.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jezz.session.domain.vo.LoginVerifyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest;
        LoginVerifyVO req = null;
        try (InputStream is = request.getInputStream()) {
            req = mapper.readValue(is, LoginVerifyVO.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        //校验管理员登陆的手机验证码,不通过不予登陆,开闭此功能请把注释取消

        authRequest = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
