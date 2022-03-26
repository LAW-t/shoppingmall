package com.example.mall.controller;

import com.example.mall.entity.BaseUser;
import com.example.mall.service.LoginService;
import com.example.mall.utils.Result;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;

/**
 * 登录
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 14:55
 */
@RestController
@RequestMapping
@Log4j2
public class LoginController {

  @Resource private LoginService loginService;

  @PostMapping("/login")
  public Result login(@RequestBody BaseUser user, HttpServletRequest request) {
    log.info(user + "用户登录");
    String token = this.loginService.login(user, request);
    return Result.success(token);
  }

  @PostMapping("/Logout")
  public Result logout() {
    log.info("用户退出登录");
    this.loginService.logout();
    return Result.success();
  }
}
