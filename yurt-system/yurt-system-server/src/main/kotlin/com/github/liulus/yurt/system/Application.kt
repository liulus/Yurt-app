package com.github.liulus.yurt.system

import com.github.liulus.yurt.jdbc.annotation.JdbcRepositoryScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 * @author liulu
 * @version V1.0
 * @since 2021/2/8
 */
@SpringBootApplication
@JdbcRepositoryScan(basePackages = ["com.github.liulus.yurt.system.repository"])
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}