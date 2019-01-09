package com.lynwood.mp.mp_crud_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lynwood.mp.mp_crud_demo.*.mapper")
public class MpCrudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpCrudDemoApplication.class, args);
    }

}

