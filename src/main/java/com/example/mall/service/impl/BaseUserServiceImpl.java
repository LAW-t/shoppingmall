package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BaseUserDao;
import com.example.mall.entity.BaseUser;
import com.example.mall.service.BaseUserService;

import org.springframework.stereotype.Service;

/**
 * 用户表(BaseUser)表服务实现类
 *
 * @author makejava
 * @since 2022-03-26 15:18:11
 */
@Service("baseUserService")
public class BaseUserServiceImpl extends ServiceImpl<BaseUserDao, BaseUser>
    implements BaseUserService {}
