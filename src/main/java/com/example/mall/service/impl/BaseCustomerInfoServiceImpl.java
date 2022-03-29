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
import com.example.mall.service.BaseCustomerInfoService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * 客户信息表(BaseCustomerInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-03-26 15:18:10
 */
@Service("baseCustomerInfoService")
@Transactional(rollbackFor = Exception.class)
public class BaseCustomerInfoServiceImpl extends ServiceImpl<BaseCustomerInfoDao, BaseCustomerInfo>
    implements BaseCustomerInfoService {

  @Resource private BaseCustomerInfoDao baseCustomerInfoDao;
  @Resource private BaseUserDao baseUserDao;

  @Override
  public boolean updateUserInfo(BaseCustomerInfo baseCustomerInfo, BaseUser currentUser) {
    // 判断是否有权限修改
    boolean renewable =
        currentUser.getType() == Authority.ADMIN.getValue()
            || Objects.equals(currentUser.getCustomerInfoId(), baseCustomerInfo.getId());
    // 如果没有权限修改，抛出异常
    if (!renewable) {
      throw new CustomException("您没有权限修改此用户");
    }
    // 不允许修改敏感信息
    baseCustomerInfo.setCreateTime(null);
    baseCustomerInfo.setUpdateTime(null);
    baseCustomerInfo.setDelFlag(null);
    baseCustomerInfo.setVersion(null);
    // 修改用户信息
    baseCustomerInfo.setVersion(
        this.baseCustomerInfoDao.selectById(baseCustomerInfo.getId()).getVersion());
    return this.baseCustomerInfoDao.updateById(baseCustomerInfo) > 0;
  }

  @Override
  public boolean deleteUserInfo(List<Long> idList, BaseUser currentUser) {
    // 判断是否有权限删除
    boolean renewable =
        currentUser.getType() == Authority.ADMIN.getValue()
            || idList.size() == 1 && Objects.equals(currentUser.getCustomerInfoId(), idList.get(0));
    // 如果没有权限删除，抛出异常
    if (!renewable) {
      throw new CustomException("您没有权限删除此用户");
    }
    // 获取用户关联的登录信息
    LambdaQueryWrapper<BaseUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(BaseUser::getCustomerInfoId, idList);
    List<Long> userIds =
        this.baseUserDao.selectList(queryWrapper).stream()
            .filter(Objects::nonNull)
            .map(BaseUser::getId)
            .collect(Collectors.toList());
    // 尝试删除用户
    int deleteBatchIds = this.baseCustomerInfoDao.deleteBatchIds(idList);
    // 尝试删除客户信息
    this.baseUserDao.deleteBatchIds(userIds);

    return deleteBatchIds > 0;
  }

  @Override
  public Page<BaseCustomerInfo> search(
      Page<BaseCustomerInfo> page, BaseCustomerInfo baseCustomerInfo) {
    LambdaQueryWrapper<BaseCustomerInfo> queryWrapper = new LambdaQueryWrapper<>();
    this.structureQuery(baseCustomerInfo, queryWrapper);
    return this.baseCustomerInfoDao.selectPage(page, queryWrapper);
  }

  private void structureQuery(
      BaseCustomerInfo baseCustomerInfo, LambdaQueryWrapper<BaseCustomerInfo> queryWrapper) {
    queryWrapper.eq(
        baseCustomerInfo.getId() != null, BaseCustomerInfo::getId, baseCustomerInfo.getId());
    queryWrapper.eq(
        baseCustomerInfo.getType() != null, BaseCustomerInfo::getType, baseCustomerInfo.getType());
    queryWrapper.like(
        StringUtils.hasText(baseCustomerInfo.getPhone()),
        BaseCustomerInfo::getPhone,
        baseCustomerInfo.getPhone());
    queryWrapper.like(
        StringUtils.hasText(baseCustomerInfo.getRealName()),
        BaseCustomerInfo::getRealName,
        baseCustomerInfo.getRealName());
    queryWrapper.like(
        StringUtils.hasText(baseCustomerInfo.getAddrProvince()),
        BaseCustomerInfo::getAddrProvince,
        baseCustomerInfo.getAddrProvince());
    queryWrapper.like(
        StringUtils.hasText(baseCustomerInfo.getAddrCity()),
        BaseCustomerInfo::getAddrCity,
        baseCustomerInfo.getAddrCity());
    queryWrapper.like(
        StringUtils.hasText(baseCustomerInfo.getAddrDistrict()),
        BaseCustomerInfo::getAddrDistrict,
        baseCustomerInfo.getAddrDistrict());
    queryWrapper.like(
        StringUtils.hasText(baseCustomerInfo.getAddrDetailed()),
        BaseCustomerInfo::getAddrDetailed,
        baseCustomerInfo.getAddrDetailed());
    queryWrapper.like(
        StringUtils.hasText(baseCustomerInfo.getEmail()),
        BaseCustomerInfo::getEmail,
        baseCustomerInfo.getEmail());
  }
}
