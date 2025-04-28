package com.ssafy.enjoytrip.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.enjoytrip.interceptor.AdminInterceptor;
import com.ssafy.enjoytrip.interceptor.ContextPathInterceptor;
import com.ssafy.enjoytrip.interceptor.UserInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/").setViewName("index");
       registry.addViewController("/place").setViewName("pages/map");
   }
   
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
	   registry.addInterceptor(new ContextPathInterceptor())
	   			.addPathPatterns("/**");
       registry.addInterceptor(new UserInterceptor())
               .addPathPatterns("");
       registry.addInterceptor(new AdminInterceptor())
       .addPathPatterns(""); 
   }
}