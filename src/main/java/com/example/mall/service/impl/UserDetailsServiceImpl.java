package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mall.dao.BaseUserDao;
import com.example.mall.entity.BaseUser;
import com.example.mall.entity.LoginUser;
import com.example.mall.enums.Authority;
import com.example.mall.exception.CustomException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.log4j.Log4j2;

/**
 * 继承spring security的UserDetailsService。 进行数据库校验。判断用户是否存在，并将用户信息封装成{@link UserDetails}对象返回。
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 14:17
 */
@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource private BaseUserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
    log.info("UserDetailsServiceImpl被调用了");
    // 查询用户信息
    LambdaQueryWrapper<BaseUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(BaseUser::getPhone, phone);
    BaseUser user = this.userDao.selectOne(queryWrapper);
    // 判断用户是否存在
    log.info("user:{}", user);
    if (Objects.isNull(user)) {
      throw new CustomException("用户不存在");
    }
    // 封装权限信息
    List<String> authorities = this.getAuthorities(user);
    log.info("authorities:{}", authorities);
    return new LoginUser(user, authorities);
  }

  private List<String> getAuthorities(BaseUser user) {
    List<String> authorities = new ArrayList<>();
    if (user.getType() == Authority.ADMIN.getValue()) {
      authorities.add(Authority.ADMIN.getName());
    } else if (user.getType() == Authority.USER.getValue()) {
      authorities.add(Authority.USER.getName());
    } else if (user.getType() == Authority.GUEST.getValue()) {
      authorities.add(Authority.GUEST.getName());
    }
    return authorities;
  }
}
