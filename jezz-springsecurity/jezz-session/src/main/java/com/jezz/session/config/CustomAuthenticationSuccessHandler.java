package com.jezz.session.config;

import com.alibaba.fastjson.JSON;
import com.jezz.session.api.APIResponse;
import com.jezz.session.domain.dto.UserSubject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws ServletException, IOException {
        clearAuthenticationAttributes(request);
        UserSubject userSubject = (UserSubject) authentication.getPrincipal();
        response.setContentType("application/json;charset=UTF-8");
        APIResponse apiResponse = APIResponse.returnSuccess(userSubject);
        response.getWriter().write(JSON.toJSONString(apiResponse));
    }
}