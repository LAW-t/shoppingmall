package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BaseUserDao;
import com.example.mall.entity.BaseUser;
import com.example.mall.service.BaseUserService;

import org.springframework.stereotype.Service;

/**
 * 用户表(BaseUser)表服务实现类
 *
 * @author tang
 * @since 2022-03-14 10:03:01
 */
@Service("baseUserService")
public class BaseUserServiceImpl extends ServiceImpl<BaseUserDao, BaseUser>
    implements BaseUserService {}
