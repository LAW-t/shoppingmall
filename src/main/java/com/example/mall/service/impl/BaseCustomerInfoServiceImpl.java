package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BaseCustomerInfoDao;
import com.example.mall.entity.BaseCustomerInfo;
import com.example.mall.service.BaseCustomerInfoService;

import org.springframework.stereotype.Service;

/**
 * 客户信息表(BaseCustomerInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-03-26 15:18:10
 */
@Service("baseCustomerInfoService")
public class BaseCustomerInfoServiceImpl extends ServiceImpl<BaseCustomerInfoDao, BaseCustomerInfo>
    implements BaseCustomerInfoService {}
