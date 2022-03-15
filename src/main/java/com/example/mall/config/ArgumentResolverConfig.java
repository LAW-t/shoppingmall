package com.example.mall.config;

import com.example.mall.resolver.UserInfoResolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import javax.annotation.Resource;

/**
 * 参数解析器的配置
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/2/9 13:52
 */
@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {
  @Resource private UserInfoResolver userInfoResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(this.userInfoResolver);
  }
}
