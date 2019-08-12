package com.jezz.dubbo.consumer.utils;

import com.jezz.dubbo.consumer.constants.DubboConstants;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Map;
import java.util.concurrent.*;

public class ThreadMDCUtils {

    public static void setTraceIdIfAbsent() {
        if (MDC.get(DubboConstants.LOG_TRACE_ID) == null) {
            MDC.put(DubboConstants.LOG_TRACE_ID, TraceIDUtils.getTraceId());
        }
    }

    public static void setTraceId() {
        MDC.put(DubboConstants.LOG_TRACE_ID, TraceIDUtils.getTraceId());
    }

    public static void setTraceId(String traceId) {
        MDC.put(DubboConstants.LOG_TRACE_ID, traceId);
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

    public static class ThreadPoolTaskExecutorMdcWrapper extends ThreadPoolTaskExecutor {
        @Override
        public void execute(Runnable task) {
            super.execute(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            super.execute(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()), startTimeout);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            return super.submitListenable(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            return super.submitListenable(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }
    }

    public static class ThreadPoolExecutorMdcWrapper extends ThreadPoolExecutor {
        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                            RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        public void execute(Runnable task) {
            super.execute(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return super.submit(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()), result);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return super.submit(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public Future<?> submit(Runnable task) {
            return super.submit(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }
    }

    public static class ForkJoinPoolMdcWrapper extends ForkJoinPool {
        public ForkJoinPoolMdcWrapper() {
            super();
        }

        public ForkJoinPoolMdcWrapper(int parallelism) {
            super(parallelism);
        }

        public ForkJoinPoolMdcWrapper(int parallelism, ForkJoinWorkerThreadFactory factory,
                                      Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
            super(parallelism, factory, handler, asyncMode);
        }

        @Override
        public void execute(Runnable task) {
            super.execute(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }

        @Override
        public <T> ForkJoinTask<T> submit(Runnable task, T result) {
            return super.submit(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()), result);
        }

        @Override
        public <T> ForkJoinTask<T> submit(Callable<T> task) {
            return super.submit(ThreadMDCUtils.wrap(task, MDC.getCopyOfContextMap()));
        }
    }
}