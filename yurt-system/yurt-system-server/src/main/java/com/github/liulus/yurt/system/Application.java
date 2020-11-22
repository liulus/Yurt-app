package com.github.liulus.yurt.system;

import com.github.liulus.yurt.jdbc.annotation.JdbcRepositoryScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liulu
 * @version V1.0
 * @since 2020/11/17
 */
@SpringBootApplication
@JdbcRepositoryScan(basePackages = "com.github.liulus.yurt.system.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
