package com.jia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.jia.mapper")
@EnableScheduling
public class JiaBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(JiaBlogApplication.class,args);
    }
}
