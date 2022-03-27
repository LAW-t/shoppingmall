package com.example.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mall.entity.BaseUser;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(BaseUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-26 16:24:15
 */
public interface BaseUserDao extends BaseMapper<BaseUser> {

  /**
   * 批量新增数据（MyBatis原生foreach方法）
   *
   * @param entities List<BaseUser> 实例对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("entities") List<BaseUser> entities);
}
