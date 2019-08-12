package com.jezz.dubbo.consumer.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;

@Slf4j
@Activate(group = {Constants.CONSUMER},order = 1)
public class TraceFilter implements Filter {

    Logger LOGGER = LoggerFactory.getLogger(TraceFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String chTraceId = MDC.get("trace_id");
        if(StringUtils.isEmpty(chTraceId)){
            chTraceId = new Date().getTime() + "-" + ((int) ((Math.random() * 9 + 1) * 100000));
        }
        LOGGER.warn("trace_id={}", chTraceId);
        context.setAttachment("trace_id", chTraceId);
        return invoker.invoke(invocation);
    }
}
