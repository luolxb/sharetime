package com.ruosen.sharetime.sharetime.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *  
 *  * @projectName ruosen-basic
 *  * @title     DruidConfig   
 *  * @package    ruosen.basic.ruosenbasic.config  
 *  * @author Administrator     
 *  * @date   2019/11/24 0024 星期日
 *  * @version V1.0.0
 *  
 */
@Configuration
public class DruidConfig {

    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

}
