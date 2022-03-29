package com.example.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.BaseUser;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户表(BaseUser)表服务接口
 *
 * @author makejava
 * @since 2022-03-26 16:24:15
 */
public interface BaseUserService extends IService<BaseUser> {
  /**
   * 获取用户列表
   *
   * @param page 页面
   * @param baseUser 基本用户
   * @return {@link List }<{@link BaseUser }>
   * @since 2022/03/27
   */
  List<BaseUser> getList(Page<BaseUser> page, BaseUser baseUser);

  /**
   * 根据id查询用户
   *
   * @param id id
   * @return {@link BaseUser }
   * @since 2022/03/27
   */
  BaseUser getUserById(Serializable id);

  /**
   * 添加管理员
   *
   * @param baseUser 基本用户
   * @param request 请求
   * @return boolean
   * @since 2022/03/27
   */
  boolean addAdmin(BaseUser baseUser, HttpServletRequest request);

  /**
   * 添加用户
   *
   * @param baseUser 基本用户
   * @param request 请求
   * @return boolean
   * @since 2022/03/27
   */
  boolean addUser(BaseUser baseUser, HttpServletRequest request);

  /**
   * 更新用户
   *
   * @param baseUser 基本用户
   * @param user 用户
   * @return boolean
   * @since 2022/03/27
   */
  boolean updateUser(BaseUser baseUser, BaseUser user);

  /**
   * 删除用户
   *
   * @param idList id列表
   * @param user 用户
   * @return boolean
   * @since 2022/03/27
   */
  int deleteUsers(List<Long> idList, BaseUser user);

  /**
   * 按关键字搜索用户
   *
   * @param id id
   * @param nickname 昵称
   * @param phone 电话
   * @return {@link List }<{@link BaseUser }>
   * @since 2022/03/29
   */
  Page<BaseUser> search(Page<BaseUser> page, String id, String nickname, String phone);
}
