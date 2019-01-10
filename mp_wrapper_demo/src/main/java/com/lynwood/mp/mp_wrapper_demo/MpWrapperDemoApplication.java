package com.lynwood.mp.mp_wrapper_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lynwood.mp.mp_wrapper_demo.*.mapper**")
public class MpWrapperDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpWrapperDemoApplication.class, args);
    }

}

