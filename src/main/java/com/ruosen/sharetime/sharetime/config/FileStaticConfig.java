package com.ruosen.sharetime.sharetime.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @PackageName: com.ruosen.sharetime.sharetime.config
 * @program: sharetime
 * @author: ruosen
 * @create: 2020-02-27 20:29
 **/

@Component
public class FileStaticConfig implements WebMvcConfigurer {

    @Value("${pathPatterns}")
    private String pathPatterns;

    @Value("${resourceLocations}")
    private String resourceLocations;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /**
         *  /source/xxx   指文件的访问方式  如：localhost:8080/source/abc.wav
         *  file:d/voice/  指静态文件存放在服务器上的位置
         */
        registry.addResourceHandler(pathPatterns).addResourceLocations("file:" + resourceLocations);
    }
}
