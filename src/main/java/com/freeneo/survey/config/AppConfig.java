package com.freeneo.survey.config;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.freeneo.survey.interceptor.CheckLoginInterceptor;
import com.freeneo.survey.interceptor.LogInterceptor;

@ComponentScan(basePackages = {"com.freeneo.survey"})
@EnableWebMvc
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

	static Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	public DataSource dataSource() {
		InitialContext cxt;
		DataSource ds = null;
		try {
			cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/freeneoSurvey");
		} catch (NamingException e) {
			logger.error(e.getMessage());
		}
		return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		return sessionFactory.getObject();
	}
	
	
	
	
	
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
    public DataSource mmsDataSource() {
        InitialContext cxt;
        DataSource ds = null;
        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/mms");
        } catch (NamingException e) {
            logger.error(e.getMessage());
        }
        return ds;
    }
    
    @Bean
    public PlatformTransactionManager mmsTransactionManager() {
        return new DataSourceTransactionManager(mmsDataSource());
    }
    
    @Bean
    public SqlSessionFactory mmsSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mmsDataSource());
        return sessionFactory.getObject();
    }

    
    
	
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
	    InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
	    internalResourceViewResolver.setPrefix("/WEB-INF/views/");
	    internalResourceViewResolver.setSuffix(".jsp");
	    return internalResourceViewResolver;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	    configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
	}

	@Bean
	public CheckLoginInterceptor SVInterceptor(){
		return new CheckLoginInterceptor();
	}
	
	@Bean
	public LogInterceptor LogInterceptor(){
		return new LogInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		
		InterceptorRegistration checkLoginInterceptor = registry.addInterceptor(SVInterceptor()); 
		checkLoginInterceptor.addPathPatterns("/*");
		checkLoginInterceptor.excludePathPatterns("/login", "/");
		
		InterceptorRegistration logInterceptor = registry.addInterceptor(LogInterceptor());
		logInterceptor.addPathPatterns("/*");
		logInterceptor.excludePathPatterns("/login", "/");
	}
	
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJacksonHttpMessageConverter());
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setPrettyPrint(true);
		return converter;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(10000000);
		return resolver;		
	}
	
}
