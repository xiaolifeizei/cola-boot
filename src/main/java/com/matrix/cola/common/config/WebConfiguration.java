package com.matrix.cola.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局Web配置
 *
 * @author : cui_feng
 * @since : 2022-05-13 13:35
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry 拦截器注册机
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
