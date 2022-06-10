package com.logbug.webdriver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.logbug.webdriver.mapper")
@SpringBootApplication
public class WebDriverApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebDriverApplication.class, args);
    }

}
