package com.jezz.session.config;

import com.alibaba.fastjson.JSON;
import com.jezz.session.api.APIResponse;
import com.jezz.session.api.ApiResponseEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {

        ApiResponseEnum[] apiResponseEnums = ApiResponseEnum.values();
        Optional<ApiResponseEnum> op = Arrays.stream(apiResponseEnums).filter(n -> n.getCode().equals(exception.getMessage())).findFirst();
        ApiResponseEnum apiResponseEnum = op.orElse(null);
        if (apiResponseEnum != null) {
            APIResponse apiResponse = APIResponse.returnFail(apiResponseEnum);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(apiResponse));
        } else {
            APIResponse apiResponse = APIResponse.returnFail(exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(apiResponse));
        }
    }
}