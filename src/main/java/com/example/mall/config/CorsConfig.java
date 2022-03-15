package com.example.mall.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 22:35
 */
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // 设置允许跨域的路径
    registry
        .addMapping("/**")
        // 设置允许跨域请求的域名
        .allowedOrigins("*")
        // 是否允许cookie
        .allowCredentials(true)
        // 设置允许的请求方式
        .allowedMethods("GET", "POST", "DELETE", "PUT")
        // 设置允许的header属性
        .allowedHeaders("*")
        // 跨域允许时间
        .maxAge(3600);
  }
}
