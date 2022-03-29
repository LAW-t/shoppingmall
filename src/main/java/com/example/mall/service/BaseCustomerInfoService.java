package com.example.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.BaseCustomerInfo;
import com.example.mall.entity.BaseUser;

import java.util.List;

/**
 * 客户信息表(BaseCustomerInfo)表服务接口
 *
 * @author makejava
 * @since 2022-03-26 15:18:10
 */
public interface BaseCustomerInfoService extends IService<BaseCustomerInfo> {
  boolean updateUserInfo(BaseCustomerInfo baseCustomerInfo, BaseUser baseUser);

  boolean deleteUserInfo(List<Long> idList, BaseUser baseUser);

  Page<BaseCustomerInfo> search(Page<BaseCustomerInfo> page, BaseCustomerInfo baseCustomerInfo);
}
