package com.ssw.blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.ssw.blog.interceptor.LoginInterceptor;


@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport
{
    @Bean
    public LoginInterceptor loginInterceptor()
    {
        return new LoginInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry)
    {
        super.addInterceptors(registry);
    }
}
