package com.ruosen.sharetime.sharetime.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  
 *  * @projectName ruosen-basic
 *  * @title     MybatisPlusConfig   
 *  * @package    ruosen.basic.ruosenbasic.config  
 *  * @author Administrator     
 *  * @date   2019/11/24 0024 星期日
 *  * @version V1.0.0
 *  
 */
@Configuration
@EnableAutoConfiguration
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

}
