package com.example.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mall.entity.BaseUser;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(BaseUser)表数据库访问层
 *
 * @author tang
 * @since 2022-03-14 10:03:01
 */
public interface BaseUserDao extends BaseMapper<BaseUser> {

  /**
   * 批量新增数据（MyBatis原生foreach方法）
   *
   * @param entities List<BaseUser> 实例对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("entities") List<BaseUser> entities);

  /**
   * 批量新增或按主键更新数据（MyBatis原生foreach方法）
   *
   * @param entities List<BaseUser> 实例对象列表
   * @return 影响行数
   * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
   */
  int insertOrUpdateBatch(@Param("entities") List<BaseUser> entities);
}
