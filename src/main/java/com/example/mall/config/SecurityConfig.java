package com.example.mall.config;

import com.example.mall.filter.JwtAuthenticationTokenFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * SpringSecurity的配置类
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 10:57
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
  @Resource private AccessDeniedHandler accessDeniedHandler;
  @Resource private AuthenticationEntryPoint authenticationEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // 关闭csrf
        .csrf()
        .disable()
        // 不通过Session获取SecurityContext
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        // 对于登录接口 允许匿名访问
        .antMatchers("/login")
        .anonymous()
        // 除上面外的所有请求全部需要鉴权认证
        .anyRequest()
        .authenticated();

    // 将JwtAuthenticationTokenFilter添加到过滤器链中
    http.addFilterBefore(
        this.jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    // 异常处理
    http.exceptionHandling()
        .authenticationEntryPoint(this.authenticationEntryPoint)
        .accessDeniedHandler(this.accessDeniedHandler);

    // 允许跨域
    http.cors();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    // 添加bean。向容器添加一个名为authenticationManagerBean的bean
    return super.authenticationManagerBean();
  }
}
