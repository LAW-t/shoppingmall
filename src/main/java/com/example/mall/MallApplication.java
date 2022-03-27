package com.example.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author tang
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.example.mall.dao")
public class MallApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallApplication.class, args);
  }
}
