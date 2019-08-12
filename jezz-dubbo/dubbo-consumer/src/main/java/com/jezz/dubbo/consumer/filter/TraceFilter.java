package com.jezz.dubbo.consumer.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.jezz.dubbo.consumer.constants.DubboConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
@Activate(group = {Constants.CONSUMER},order = 1)
public class TraceFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String chTraceId = MDC.get(DubboConstants.LOG_TRACE_ID);
        log.warn("trace_id={}", chTraceId);
        context.setAttachment("trace_id", chTraceId);
        return invoker.invoke(invocation);
    }
}
