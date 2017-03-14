package com.spitter.config;

import java.util.Properties;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.spitter.domain.User;
import com.thoughtworks.xstream.io.xml.StaxDriver;

@Configuration
@EnableWebMvc
@ComponentScan("com.spitter.controller")
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tiles = new TilesConfigurer();
		tiles.setDefinitions(new String[] { "/WEB-INF/views/layout/tiles.xml" });
		tiles.setCheckRefresh(true);
		return tiles;
	}

	@Bean
	public ViewResolver viewResolver() {
		return new TilesViewResolver();
	}

	// Excel及PDF视图解析器配置
	@Bean
	public ViewResolver beanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(10);
		return beanNameViewResolver;
	}

	// 错误消息和显示标签的国际化
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:LabelMessages", "classpath:ValidationMessages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(0);
		Properties properties = new Properties();
		properties.put("fileEncodings", "UTF-8");
		messageSource.setFileEncodings(properties);
		return messageSource;
	}

	//输出XML
	@Bean
	public XStreamMarshaller xstreamMarshaller() {
		XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
		xstreamMarshaller.setStreamDriver(new StaxDriver());
		xstreamMarshaller.setAnnotatedClasses(User.class);
		return xstreamMarshaller;
	}

	@Bean(name = "userListXML")
	public MarshallingView marshallingView() {
		MarshallingView marshallingView = new MarshallingView();
		marshallingView.setModelKey("userList");
		marshallingView.setMarshaller(xstreamMarshaller());
		return marshallingView;
	}

	//输出JSON
	@Bean(name = "userListJSON")
	public MappingJackson2JsonView mappingJackson2JsonView() {
		MappingJackson2JsonView mappingJacksonJson2View = new MappingJackson2JsonView();
		mappingJacksonJson2View.setModelKey("userList");
		return mappingJacksonJson2View;
	}

}
