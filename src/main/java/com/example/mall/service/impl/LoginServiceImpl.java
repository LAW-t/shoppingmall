package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BaseUserDao;
import com.example.mall.entity.BaseUser;
import com.example.mall.entity.LoginUser;
import com.example.mall.exception.CustomException;
import com.example.mall.service.LoginService;
import com.example.mall.utils.IpUtil;
import com.example.mall.utils.JwtUtil;
import com.example.mall.utils.RedisCache;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;

/**
 * 登录服务类
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 14:59
 */
@Service
@Log4j2
public class LoginServiceImpl extends ServiceImpl<BaseUserDao, BaseUser> implements LoginService {
  @Resource() private AuthenticationManager authenticationManager;
  @Resource() private RedisCache redisCache;
  @Resource() private BaseUserDao baseUserDao;

  @Override
  public String login(BaseUser user, HttpServletRequest request) {
    // AuthenticationManager authenticate 进行用户认证
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(user.getPhone(), user.getPassword());
    Authentication authenticate = this.authenticationManager.authenticate(authenticationToken);
    // 如果认证没有通过，则返回错误信息
    if (!authenticate.isAuthenticated()) {
      throw new CustomException("用户名或密码错误");
    }
    // 如果认证通过，则生成jwt token
    LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
    String userName = authenticationToken.getName();
    String jwt = JwtUtil.createJWT(userName);
    // 修改用户登录时间和登录ip
    user.setLastLoginTime(LocalDateTime.now());
    String ipAddr = IpUtil.getIpAddr(request);
    long ip = IpUtil.ip2Long(ipAddr);
    user.setLastLoginIp(ip);
    // 将用户登录时间和登录ip更新到数据库
    this.baseUserDao.updateById(user);
    // 将用户信息和token分别存入redis
    this.redisCache.setCacheObject(
        "user:info:" + userName, loginUser, 3 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
    this.redisCache.setCacheObject(
        "user:token:" + userName, jwt, 3 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
    return jwt;
  }

  @Override
  public boolean logout() {
    UsernamePasswordAuthenticationToken authenticationToken =
        (UsernamePasswordAuthenticationToken)
            SecurityContextHolder.getContext().getAuthentication();
    String userName = authenticationToken.getName();
    log.info("退出登录，用户名：{}", userName);
    this.redisCache.deleteObject("user:info:" + userName);
    this.redisCache.deleteObject("user:token:" + userName);
    return true;
  }
}
