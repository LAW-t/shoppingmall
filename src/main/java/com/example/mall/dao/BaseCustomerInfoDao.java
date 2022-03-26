package com.example.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mall.entity.BaseCustomerInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户信息表(BaseCustomerInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-26 15:18:10
 */
public interface BaseCustomerInfoDao extends BaseMapper<BaseCustomerInfo> {

  /**
   * 批量新增数据（MyBatis原生foreach方法）
   *
   * @param entities List<BaseCustomerInfo> 实例对象列表
   * @return 影响行数
   */
  int insertBatch(@Param("entities") List<BaseCustomerInfo> entities);

  /**
   * 批量新增或按主键更新数据（MyBatis原生foreach方法）
   *
   * @param entities List<BaseCustomerInfo> 实例对象列表
   * @return 影响行数
   * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
   */
  int updateOne(@Param("entities") BaseCustomerInfo entities);
}
