package com.education.project.config;

import com.education.project.interceoter.InterCenter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterCenter()).addPathPatterns("/**").
                excludePathPatterns("/v1/api/user/wxLogin").
                excludePathPatterns("/v1/api/admin/axiosLogin").
                excludePathPatterns("/login").
                excludePathPatterns("/vue/**").
                excludePathPatterns("/css/**").
                excludePathPatterns("/js/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
