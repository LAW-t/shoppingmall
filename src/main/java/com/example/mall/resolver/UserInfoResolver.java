package com.example.mall.resolver;

import com.example.mall.annotations.UserInfo;
import com.example.mall.entity.LoginUser;
import com.example.mall.utils.RedisCache;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

import javax.annotation.Resource;

import lombok.extern.log4j.Log4j2;

/**
 * 用户信息解析器
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/15 15:06
 */
@Component
@Log4j2
public class UserInfoResolver implements HandlerMethodArgumentResolver {
  @Resource private RedisCache redisCache;

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    log.info(Arrays.toString(methodParameter.getParameterAnnotations()));
    return methodParameter.hasParameterAnnotation(UserInfo.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer,
      NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory)
      throws Exception {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    LoginUser loginUser = this.redisCache.getCacheObject("user:info:" + name);
    return loginUser.getUser();
  }
}
