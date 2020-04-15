package org.nohi.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-03-30 20:07
 **/
@MapperScan("org.nohi.mybatis.**.dao")
@SpringBootApplication
public class ApplicationMybatis {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMybatis.class, args);
    }
}
