package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mall.dao.BaseCustomerInfoDao;
import com.example.mall.dao.BaseUserDao;
import com.example.mall.entity.BaseCustomerInfo;
import com.example.mall.entity.BaseUser;
import com.example.mall.enums.Authority;
import com.example.mall.exception.CustomException;
import com.example.mall.service.BaseUserService;
import com.example.mall.utils.IpUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;

/**
 * 用户表(BaseUser)表服务实现类
 *
 * @author makejava
 * @since 2022-03-26 16:24:15
 */
@Service("baseUserService")
@Transactional(rollbackFor = Exception.class)
@Log4j2
public class BaseUserServiceImpl extends ServiceImpl<BaseUserDao, BaseUser>
    implements BaseUserService {
  @Resource private BaseUserDao baseUserDao;
  @Resource private BaseCustomerInfoDao baseCustomerInfoDao;

  @Override
  public List<BaseUser> getList(Page<BaseUser> page, BaseUser baseUser) {
    List<BaseUser> baseUsers = this.baseUserDao.selectList(null);
    for (BaseUser user : baseUsers) {
      this.parseIp(user);
    }
    return baseUsers;
  }

  @Override
  public BaseUser getUserById(Serializable id) {
    BaseUser user = this.baseUserDao.selectById(id);
    this.parseIp(user);
    return user;
  }

  @Override
  public boolean addAdmin(BaseUser baseUser, HttpServletRequest request) {
    // TODO 待增加验证码功能
    // 判断是否存在相同手机号
    this.exist(baseUser);
    // 填入默认值
    this.setDefaultValue(baseUser, Authority.ADMIN, 0L, request);
    // 插入用户
    int insert = this.baseUserDao.insert(baseUser);
    return insert > 0;
  }

  @Override
  public boolean addUser(BaseUser baseUser, HttpServletRequest request) {
    // TODO 待增加验证码功能
    // 判断是否存在相同手机号
    this.exist(baseUser);
    // 创建关联客户信息
    BaseCustomerInfo baseCustomerInfo = new BaseCustomerInfo();
    baseCustomerInfo.setPhone(baseUser.getPhone());
    this.baseCustomerInfoDao.insert(baseCustomerInfo);
    // 填入默认值
    this.setDefaultValue(baseUser, Authority.USER, baseCustomerInfo.getId(), request);
    // 插入用户
    int insert = this.baseUserDao.insert(baseUser);
    return insert > 0;
  }

  private void setDefaultValue(
      BaseUser baseUser, Authority user, long customerInfoId, HttpServletRequest request) {
    // 填入默认值
    baseUser.setNickname(baseUser.getPhone());
    baseUser.setType(user.getValue());
    baseUser.setCustomerInfoId(customerInfoId);
    baseUser.setLastLoginTime(LocalDateTime.now());
    // 获取ip
    String ipAddr = IpUtil.getIpAddr(request);
    baseUser.setLastLoginIp(IpUtil.ip2Long(ipAddr));
    // 密码加密
    String password = baseUser.getPassword();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    password = passwordEncoder.encode(password);
    baseUser.setPassword(password);
  }

  private void parseIp(BaseUser user) {
    Long lastLoginIp = user.getLastLoginIp();
    String ip = IpUtil.long2Ip(lastLoginIp);
    user.setIp(ip);
  }

  private void exist(BaseUser baseUser) {
    LambdaQueryWrapper<BaseUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseUser::getPhone, baseUser.getPhone());
    BaseUser user = baseUser.selectOne(queryWrapper);
    if (user != null) {
      throw new CustomException("该手机号已被注册");
    }
  }
}
