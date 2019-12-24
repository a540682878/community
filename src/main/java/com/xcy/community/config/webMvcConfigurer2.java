package com.xcy.community.config;

import com.xcy.community.component.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//所有的WebMvcConfigurer组件都会一起起作用
@Configuration
public class webMvcConfigurer2 implements WebMvcConfigurer {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送/xcy请求  来到login.html
        registry.addViewController("/xcy").setViewName("index");
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){

        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer(){
            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {

                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/callback","/","/static/**","/webjars/**");
            }
        };

        return webMvcConfigurer;
    }

}

