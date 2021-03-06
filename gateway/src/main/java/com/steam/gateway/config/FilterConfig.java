package com.steam.gateway.config;

import com.steam.gateway.filter.JwtVlidationFilter;
import com.steam.gateway.filter.PermissionCheckFilter;
import com.steam.gateway.jwt.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final JwtUtil jwtUtil;

    public FilterConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public FilterRegistrationBean<JwtVlidationFilter> jwtValidationFilterRegister() {
        FilterRegistrationBean<JwtVlidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtVlidationFilter(jwtUtil));
        registrationBean.setOrder(1);
//        registrationBean.addUrlPatterns("/**");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<PermissionCheckFilter> permissionCheckFilterRegister() {
        FilterRegistrationBean<PermissionCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PermissionCheckFilter(jwtUtil));
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns("/management/**");
        return registrationBean;
    }
}
