package com.jezz.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jezz.server.domain.LoginVerifyReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String cacheCode = "123";// 可以自定义获取保存到redis的验证码
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest;
        // 将request转成请求对象
        LoginVerifyReq req = null;
        try (InputStream is = httpServletRequest.getInputStream()) {
            req = mapper.readValue(is, LoginVerifyReq.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        authRequest = new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword());
        // 可以将spring session放进去
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(httpServletRequest));
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
