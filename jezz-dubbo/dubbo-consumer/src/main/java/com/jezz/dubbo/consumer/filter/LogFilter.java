package com.jezz.dubbo.consumer.filter;

import com.jezz.dubbo.consumer.utils.ThreadMDCUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Order(0)
@Component
public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ThreadMDCUtils.setTraceIdIfAbsent();
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
