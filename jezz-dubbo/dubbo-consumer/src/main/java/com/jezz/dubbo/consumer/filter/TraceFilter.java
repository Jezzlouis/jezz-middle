package com.jezz.dubbo.consumer.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@Slf4j
@Activate(group = {Constants.CONSUMER},order = 1)
public class TraceFilter implements Filter {

    Logger LOGGER = LoggerFactory.getLogger(TraceFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String chTraceId = MDC.get("traceId");
        LOGGER.debug("chTraceId={}", chTraceId);
        context.setAttachment("chTraceId", chTraceId);
        return invoker.invoke(invocation);
    }
}
