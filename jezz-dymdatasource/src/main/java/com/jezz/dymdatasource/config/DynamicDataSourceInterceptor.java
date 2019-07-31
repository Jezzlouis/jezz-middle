package com.jezz.dymdatasource.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Aspect
@Component
public class DynamicDataSourceInterceptor {

    @Pointcut("@annotation(com.jezz.dymdatasource.config.DataBase)")
    public void service() {

    }
    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     * @param point
     * @throws Exception
     */
    @Before("service()")
    public void intercept(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        resolveDataSource(target, signature.getMethod());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            if (clazz.isAnnotationPresent(DataBase.class)) {
                DataBase source = clazz.getAnnotation(DataBase.class);
                DynamicDataSourceHolder.setDataSourceType(source.value());
            }
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataBase.class)) {
                DataBase source = m.getAnnotation(DataBase.class);
                DynamicDataSourceHolder.setDataSourceType(source.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("service()")
    public void afterSwitchDS(JoinPoint point){
        DynamicDataSourceHolder.clearDataSourceType();
    }
}