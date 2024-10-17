package org.boot.post_springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class PostSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostSpringbootApplication.class, args);
    }

}
