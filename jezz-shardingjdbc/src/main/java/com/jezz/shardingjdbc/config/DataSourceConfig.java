package com.jezz.shardingjdbc.config;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() throws Exception{
        //1.设置分库映射
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("ds_0", createDataSource("ds_0"));

        //2.设置分表映射，将t_order_0和t_order_1两个实际的表映射到t_order逻辑表
        TableRuleConfiguration tableRuleConfig = userRuleConfig();

        //3.具体的分表策略
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(tableRuleConfig);
        Properties p = new Properties();
        p.setProperty("sql.show",Boolean.TRUE.toString());
        // 获取数据源对象
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), p);
    }

    private DataSource createDataSource(String dataSourceName) {
        //使用druid连接数据库
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl(String.format("jdbc:mysql://localhost:3306/%s?characterEncoding=utf-8", dataSourceName));
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("123456");
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
    // 每个分区最大的连接数
        druidDataSource.setMaxActive(20);
    // 每个分区最小的连接数
        druidDataSource.setMinIdle(5);
		return druidDataSource;
    }

    /**
     * order表分片策略
     * @return
     */
    private TableRuleConfiguration userRuleConfig() {
        // 配置user表规则
        TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration();
        tableRuleConfig.setLogicTable("t_order");
        tableRuleConfig.setActualDataNodes("ds_${0}.t_order_${0..1}");
        tableRuleConfig.setKeyGeneratorColumnName("order_id");
        // 配置分表策略
        tableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "t_order_${user_id % 2}"));
        return tableRuleConfig;
    }

    @Bean
    public Filter statFilter(){
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(5000);
        filter.setLogSlowSql(true);
        filter.setMergeSql(true);
        return filter;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Scope(value = "prototype")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean
//    public ServletRegistrationBean druidServlet() {
//        String druidServletUrl = "/druid/*";
//        return new ServletRegistrationBean(new StatViewServlet(), druidServletUrl);
//    }
}