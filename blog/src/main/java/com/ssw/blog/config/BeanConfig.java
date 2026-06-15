package com.ssw.blog.config;

import com.ssw.blog.utils.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean容器
 */
@Configuration
public class BeanConfig {
    @Bean
    public IdWorker idWorker(){
        return  new IdWorker();
    }
}
