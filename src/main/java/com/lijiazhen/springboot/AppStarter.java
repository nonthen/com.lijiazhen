package com.lijiazhen.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication( exclude= {DataSourceAutoConfiguration.class})
public class AppStarter {
    public static void main(String[] args) {

        SpringApplication.run(AppStarter.class,args);

    }
}
