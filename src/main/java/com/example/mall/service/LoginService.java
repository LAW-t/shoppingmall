package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.BaseUser;
import com.example.mall.vo.UserInfoVo;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录服务接口
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 14:57
 */
public interface LoginService extends IService<BaseUser> {
  /**
   * 登录
   *
   * @param user 用户
   * @param request
   * @return 登录结果
   */
  UserInfoVo login(BaseUser user, HttpServletRequest request);
  /**
   * 退出登录
   *
   * @return 登录结果
   */
  boolean logout();
}
