package com.jezz.dymdatasource.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public ServletRegistrationBean druidServlet() {
        String druidServletUrl = "/druid/*";
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), druidServletUrl);
        return registrationBean;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Scope(value = "prototype")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }

    @Autowired
    WallFilter wallFilter;

    @Bean(name = "wallConfig")
    WallConfig wallFilterConfig() {
        WallConfig wc = new WallConfig();
        wc.setMultiStatementAllow(true);
        return wc;
    }

    @Bean(name = "wallFilter")
    @DependsOn("wallConfig")
    WallFilter wallFilter(WallConfig wallConfig) {
        WallFilter wfilter = new WallFilter();
        wfilter.setConfig(wallConfig);
        return wfilter;
    }
    /**
     * db1数据库配置
     */
    @Bean("db1")
    @ConfigurationProperties(prefix = "db1.datasource")
    public DataSource db1Source() {
        return DataSourceBuilder.create().build();
    }

    /**
     * db2数据库配置
     */
    @Bean("db2")
    @ConfigurationProperties(prefix = "db2.datasource")
    public DataSource db2Source() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据库配置
     */
    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(db1Source());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap<>(5);
        dsMap.put("db1", db1Source());
        dsMap.put("db2", db2Source());

        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
