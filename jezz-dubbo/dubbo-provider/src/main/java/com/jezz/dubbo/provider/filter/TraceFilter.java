package com.jezz.dubbo.provider.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
@Slf4j
@Activate(group = {Constants.PROVIDER},order = 1)
public class TraceFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String chTraceId = context.getAttachment("trace_id") + "&client=" + context.getRemoteAddressString();
        log.warn("trace_id={}", chTraceId);
        if (StringUtils.isNotEmpty(chTraceId)) {
            MDC.put("trace_id", chTraceId);
        }
        Result result = invoker.invoke(invocation);
        if (StringUtils.isNotEmpty(chTraceId)) {
            MDC.clear();
        }
        return result;
    }
}
