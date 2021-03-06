package com.miaotech.collection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.miaotech.collection.mapper")
public class CollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionApplication.class, args);
    }

}
