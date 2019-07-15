package io.transwarp.esb.config;

import io.transwarp.esb.aop.logging.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wangyan
 * @description
 * @date 2019/7/12 10:19
 */

@Configuration
public class BaseInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    BaseInterceptor baseInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //排除此路径，其他都拦截
//        registry.addInterceptor(baseInterceptor).excludePathPatterns("/base/test");
        //指定路径，只拦截此路径
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/base/test")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        //如果两个同时存在，排除生效，指定的路径会拦截两次
        //添加拦截器
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
