package com.example.mall.filter;

import com.example.mall.entity.AllowAccess;
import com.example.mall.entity.LoginUser;
import com.example.mall.exception.CustomException;
import com.example.mall.utils.JwtUtil;
import com.example.mall.utils.RedisCache;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;

/**
 * jwt认证过滤器
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 19:04
 */
@Component
@Log4j2
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Resource private RedisCache redisCache;
  @Resource private AllowAccess allowAccess;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // 如果是允许访问的url，直接放行
    for (String url : this.allowAccess.getUrl()) {
      if (request.getRequestURI().contains(url)) {
        filterChain.doFilter(request, response);
        return;
      }
    }
    // 获取请求头中的token
    String token = request.getHeader("Authorization");
    // 判断token是否为空
    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
      token = token.substring(7);
      // 解析token
      String userId = this.parseJwt(token);
      // 从redis中获取用户信息
      LoginUser loginUser = this.getLoginUser4Redis(userId, token);
      // 存入SecurityContextHolder
      this.saveSecurityContextHolder(loginUser);
    }
    // 放行
    filterChain.doFilter(request, response);
  }

  private void saveSecurityContextHolder(LoginUser loginUser) {
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private LoginUser getLoginUser4Redis(String userId, String curToken) {
    String tokenKey = "user:token:" + userId;
    String oldToken = this.redisCache.getCacheObject(tokenKey);
    if (Objects.isNull(oldToken)) {
      throw new CustomException("登录过期，请重新登录");
    }
    if (!oldToken.equals(curToken)) {
      throw new CustomException("您已经在其他地方登录，请重新登录");
    }
    String redisKey = "user:info:" + userId;
    return this.redisCache.getCacheObject(redisKey);
  }

  private String parseJwt(String token) {
    String userId;
    try {
      Claims claims = JwtUtil.parseJWT(token);
      userId = claims.getSubject();
    } catch (Exception e) {
      throw new CustomException("token解析失败");
    }
    return userId;
  }
}
