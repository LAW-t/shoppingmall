package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BaseCustomerInfoDao;
import com.example.mall.dao.BaseUserDao;
import com.example.mall.entity.BaseCustomerInfo;
import com.example.mall.entity.BaseUser;
import com.example.mall.entity.LoginUser;
import com.example.mall.exception.CustomException;
import com.example.mall.service.LoginService;
import com.example.mall.utils.IpUtil;
import com.example.mall.utils.JwtUtil;
import com.example.mall.utils.RedisCache;
import com.example.mall.vo.UserInfoVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
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
  @Resource() private BaseCustomerInfoDao baseCustomerInfoDao;

  @Value("${timeout.token:21600000}")
  @NestedConfigurationProperty
  private int timeout;

  @Override
  public UserInfoVo login(BaseUser user, HttpServletRequest request) {
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
    user = loginUser.getUser();
    user.setLastLoginTime(LocalDateTime.now());
    String ipAddr = IpUtil.getIpAddr(request);
    long ip = IpUtil.ip2Long(ipAddr);
    user.setLastLoginIp(ip);
    // 将用户登录时间和登录ip更新到数据库
    this.baseUserDao.updateById(user);
    // 获取用户信息的vo对象
    UserInfoVo userInfo = this.getUserInfo(user, jwt);
    // 将用户信息和token分别存入redis
    this.redisCache.setCacheObject(
        "user:info:" + userName, loginUser, this.timeout, TimeUnit.MILLISECONDS);
    this.redisCache.setCacheObject(
        "user:token:" + userName, jwt, this.timeout, TimeUnit.MILLISECONDS);
    // 返回用户信息
    return userInfo;
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

  private UserInfoVo getUserInfo(BaseUser user, String jwt) {
    // 获取客户信息
    UserInfoVo userInfoVo = new UserInfoVo();
    if (user.getCustomerInfoId() != 0) {
      BaseCustomerInfo baseCustomerInfo =
          this.baseCustomerInfoDao.selectById(user.getCustomerInfoId());
      userInfoVo.setRealName(baseCustomerInfo.getRealName());
      userInfoVo.setAddrProvince(baseCustomerInfo.getAddrProvince());
      userInfoVo.setAddrCity(baseCustomerInfo.getAddrCity());
      userInfoVo.setAddrDistrict(baseCustomerInfo.getAddrDistrict());
      userInfoVo.setAddrDetailed(baseCustomerInfo.getAddrDetailed());
      userInfoVo.setEmail(baseCustomerInfo.getEmail());
    }
    userInfoVo.setId(user.getId());
    userInfoVo.setPhone(user.getPhone());
    userInfoVo.setType(user.getType());
    userInfoVo.setNickname(user.getNickname());
    userInfoVo.setToken(jwt);
    return userInfoVo;
  }
}
