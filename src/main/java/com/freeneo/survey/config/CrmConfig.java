package com.freeneo.survey.config;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.freeneo.survey.CheckLoginInterceptor;

@Configuration
public class CrmConfig extends WebMvcConfigurerAdapter {

	static Logger logger = LoggerFactory.getLogger(CrmConfig.class);
	
	@Bean
    public DataSource crmDataSource() {
        InitialContext cxt;
        DataSource ds = null;
        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/crm");
        } catch (NamingException e) {
            logger.error(e.getMessage());
        }
        return ds;
    }
    
    @Bean
    public PlatformTransactionManager crmTransactionManager() {
        return new DataSourceTransactionManager(crmDataSource());
    }

    @Bean
    public SqlSessionFactory crmSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(crmDataSource());
        return sessionFactory.getObject();
    }
    
    @Bean
	public MapperScannerConfigurer crmMapper(){
		MapperScannerConfigurer mapper = new MapperScannerConfigurer();
		mapper.setBasePackage("com.freeneo.survey.mapperCrm");
		mapper.setSqlSessionFactoryBeanName("crmSqlSessionFactory");
		return mapper;
	}

}
