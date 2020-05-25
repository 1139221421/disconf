package com.baidu.disconf.web.config;

import com.baidu.dsp.common.interceptor.login.LoginInterceptor;
import com.baidu.dsp.common.interceptor.session.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Arrays;

/**
 * web配置
 */
@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/freemarker/");
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        loginInterceptor.setNotInterceptPathList(Arrays.asList("/api/account/signin", "" +
                        " /api/zoo/hosts", "/api/zoo/prefix", "/api/config/item", "/api/config/file"
                , "/api/config/list", "/api/config/simple/list"));
        loginInterceptor.setNotJsonPathList(Arrays.asList("/"));
        return loginInterceptor;
    }

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/dep/**", "/assets/**", "/mainTpl/**", "/tools/**", "/copyconfig.html", "/newenv.html",
                        "/main.html", "/modifyFile.html", "/modifyItem.html", "/modifypassword.html", "/newapp.html"
                        , "/index.html", "/newconfig.html", "/login.html", "/newconfig_file.html", "/newconfig_item.html");
        registry.addInterceptor(loginInterceptor()).order(10).addPathPatterns("/**")
                .excludePathPatterns("/dep/**", "/assets/**", "/mainTpl/**", "/tools/**", "/copyconfig.html", "/newenv.html",
                        "/main.html", "/modifyFile.html", "/modifyItem.html", "/modifypassword.html", "/newapp.html"
                        , "/index.html", "/newconfig.html", "/login.html", "/newconfig_file.html", "/newconfig_item.html");

    }

    @Bean
    public FormContentFilter formContentFilter() {
        return new FormContentFilter();
    }
}
