package com.macro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 启动类
 */
@SpringBootApplication
@MapperScan("com.macro.common.mapper")
public class MallSwarmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallSwarmApplication.class, args);
    }
}
