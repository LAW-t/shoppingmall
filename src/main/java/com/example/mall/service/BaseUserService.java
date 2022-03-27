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
  List<BaseUser> getList(Page<BaseUser> page, BaseUser baseUser);

  BaseUser getUserById(Serializable id);

  boolean addAdmin(BaseUser baseUser, HttpServletRequest request);

  boolean addUser(BaseUser baseUser, HttpServletRequest request);
}
