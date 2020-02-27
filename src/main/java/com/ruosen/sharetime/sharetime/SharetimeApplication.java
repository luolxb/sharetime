package com.ruosen.sharetime.sharetime;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ruosen
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.ruosen.sharetime.sharetime.dao")
public class SharetimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SharetimeApplication.class, args);
    }

}
