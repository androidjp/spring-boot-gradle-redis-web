package com.jp.springbootgradleredisweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jp.springbootgradleredisweb.api.mappers")
public class SpringBootGradleRedisWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGradleRedisWebApplication.class, args);
    }
}
