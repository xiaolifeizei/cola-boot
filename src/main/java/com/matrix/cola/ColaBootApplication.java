package com.matrix.cola;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author : cui_feng
 * @since : 2023-06-13 12:18
 */
@SpringBootApplication
@MapperScan("com.matrix.cola.**.mapper")
public class ColaBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ColaBootApplication.class,args);
    }
}
