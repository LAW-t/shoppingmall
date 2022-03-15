package com.example.mall.handler;

import com.alibaba.fastjson.JSON;
import com.example.mall.utils.Result;
import com.example.mall.utils.WebUtil;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证异常处理器
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/15 10:33
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e)
      throws IOException, ServletException {
    Result result = new Result();
    result.success(false).code(401).data(null);
    String jsonString = JSON.toJSONString(result);
    WebUtil.renderString(httpServletResponse, jsonString);
  }
}
