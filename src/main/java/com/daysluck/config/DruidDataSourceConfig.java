package com.daysluck.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration  
@EnableTransactionManagement  
//Druid的DataResource配置类 
public class DruidDataSourceConfig   implements EnvironmentAware {  
    @SuppressWarnings("unused")
	private RelaxedPropertyResolver propertyResolver;  
  
    public void setEnvironment(Environment env) {  
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");  
    }  
}