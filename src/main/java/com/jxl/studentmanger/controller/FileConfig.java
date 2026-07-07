package com.jxl.studentmanger.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Value("${file.server.dir}")
    private String fileDir = "";
    @Value("${file.server.path}")
    private String path = "";
    /**
     * 配置资源处理器，以处理静态资源请求。
     * 此方法用于告诉Spring Boot应用在哪里寻找静态资源，并如何处理这些资源的请求。
     *
     * @param registry 资源处理器注册表，用于注册和管理资源处理器。
     *                 通过此注册表，可以指定哪些URL模式应该由特定的资源处理器处理。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 为所有URL路径配置资源处理器，资源位置指向文件系统中的指定目录。
        // 这样，应用可以从该目录中动态地服务静态资源。
        registry.addResourceHandler("/"+path+"/**").addResourceLocations("file:" + fileDir);
    }
}