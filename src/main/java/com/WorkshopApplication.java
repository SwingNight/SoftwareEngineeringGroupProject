package com;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = {"com.dao"}) //scanDAO
public class WorkshopApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(WorkshopApplication.class, args);
    }
}
