package com.daysluck.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 拦截设置本地线程变量 
 * @author zhangcb
 *
 */
@Aspect  
@Component
public class DataSourceAop {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before("execution(* com.demo.mapper..*.select*(..)) || execution(* com.demo.mapper..*.get*(..))")  
    public void setReadDataSourceType() {  
        DataSourceContextHolder.read();  
        logger.info("dataSource切换到：Read");  
    }  
  
    @Before("execution(* com.demo.mapper..*.insert*(..)) || execution(* com.demo.mapper..*.update*(..))")  
    public void setWriteDataSourceType() {  
        DataSourceContextHolder.write();  
        logger.info("dataSource切换到：write");  
    }  
}
