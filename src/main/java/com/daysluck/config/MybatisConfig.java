package com.daysluck.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@Configuration  
@ConditionalOnClass({EnableTransactionManagement.class})  
@Import({ DataBaseConfiguration.class})  
@MapperScan(basePackages={"com.demo.mapper"})  
public class MybatisConfig {  
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Value("${spring.datasource.type}")  
    private Class<? extends DataSource> dataSourceType;  
  
    @Value("${datasource.readSize}")  
    private String dataSourceSize;  
    
    @Resource(name = "writeDataSource")  
    private DataSource dataSource;
    
    @Resource(name = "readDataSources")  
    private List<DataSource> readDataSources;  
  
    @Bean  
    @ConditionalOnMissingBean  
    public SqlSessionFactory sqlSessionFactory() throws Exception {  
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();  
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());  
        sqlSessionFactoryBean.setTypeAliasesPackage("com.demo.model");  
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);  
        return sqlSessionFactoryBean.getObject();  
    }  
    /** 
     * 有多少个数据源就要配置多少个bean 
     * @return 
     */  
    @Bean  
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {  
        int size = Integer.parseInt(dataSourceSize);  
        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);  
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();  
        // DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");  
        // 写  
        targetDataSources.put(DataSourceType.write.getType(),dataSource);  
        // targetDataSources.put(DataSourceType.read.getType(),readDataSource);  
        //多个读数据库时  
        for (int i = 0; i < size; i++) {  
            targetDataSources.put(i, readDataSources.get(i));  
        }  
        proxy.setDefaultTargetDataSource(dataSource);  
        proxy.setTargetDataSources(targetDataSources);  
        return proxy;  
    }  
    
    /**  
     * 配置事务管理器  
     */  
    @Bean(name = "transactionManager")  
    @Primary  
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) throws Exception {  
        return new DataSourceTransactionManager(dataSource);  
    }  
    
    /**
     * 注册分页插件
     * @return
     */
    @Bean
    public PageHelper pageHelper() {
       System.out.println("MyBatisConfiguration.pageHelper()");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
