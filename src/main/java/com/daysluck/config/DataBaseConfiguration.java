package com.daysluck.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据库配置：解析properties文件 
 * @author zhangcb
 *
 */
@Configuration 
public class DataBaseConfiguration {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.datasource.type}")  
    private Class<? extends DataSource> dataSourceType;  
  
    @Bean(name="writeDataSource", destroyMethod = "close", initMethod="init")  
    @Primary  
    @ConfigurationProperties(prefix = "spring.datasource",locations = "classpath:mybatis/mybatis.properties")  
    public DataSource writeDataSource() {  
    	logger.info("-------------------- writeDataSource init ---------------------");  
        return DataSourceBuilder.create().type(dataSourceType).build();  
    }  
    /** 
     * 有多少个从库就要配置多少个 
     * @return 
     */  
    @Bean(name = "readDataSource1")  
    @ConfigurationProperties(prefix = "spring.slave",locations = "classpath:mybatis/mybatis.properties")  
    public DataSource readDataSourceOne(){  
    	logger.info("-------------------- readDataSourceOne init ---------------------");  
        return DataSourceBuilder.create().type(dataSourceType).build();  
    }  
  
    @Bean(name = "readDataSource2")  
    @ConfigurationProperties(prefix = "spring.read2",locations = "classpath:mybatis/mybatis.properties")  
    public DataSource readDataSourceTwo() {  
    	logger.info("-------------------- readDataSourceTwo init ---------------------");  
        return DataSourceBuilder.create().type(dataSourceType).build();  
    }  
    @Bean("readDataSources")  
    public List<DataSource> readDataSources(){  
        List<DataSource> dataSources=new ArrayList<>();  
        dataSources.add(readDataSourceOne());  
        dataSources.add(readDataSourceTwo());  
        return dataSources;  
    }  
}
