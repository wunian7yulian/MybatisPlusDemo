package com.lynwood.mp.simpledemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lynwood.mp.simpledemo.mapper")
public class SimpledemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpledemoApplication.class, args);
    }

}

