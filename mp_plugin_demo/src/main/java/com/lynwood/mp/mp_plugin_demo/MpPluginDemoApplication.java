package com.lynwood.mp.mp_plugin_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lynwood.mp.mp_plugin_demo.*.mapper**")
public class MpPluginDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpPluginDemoApplication.class, args);
    }

}

