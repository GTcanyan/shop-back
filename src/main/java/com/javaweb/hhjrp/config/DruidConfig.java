package com.javaweb.hhjrp.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {
    //主要作用就是定义了一个Druid数据源的Bean，并且根据配置文件中的属性进行了初始化，使得在应用程序中可以方便地使用Druid数据源。
    @Bean(destroyMethod = "close",initMethod = "init")
//    配置这里使得application.yml里面的一些配置可以起作用
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        return new DruidDataSource();
    }
}
