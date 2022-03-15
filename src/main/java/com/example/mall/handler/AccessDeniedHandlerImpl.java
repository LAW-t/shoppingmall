package com.example.mall.handler;

import com.alibaba.fastjson.JSON;
import com.example.mall.utils.Result;
import com.example.mall.utils.WebUtil;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限异常处理器
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/15 10:36
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AccessDeniedException e)
      throws IOException, ServletException {
    Result result = new Result();
    result.success(false).message("权限不足" + e.getMessage()).code(403).data(null);
    String jsonString = JSON.toJSONString(result);
    WebUtil.renderString(httpServletResponse, jsonString);
  }
}
