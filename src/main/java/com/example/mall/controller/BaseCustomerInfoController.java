package com.example.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.entity.BaseCustomerInfo;
import com.example.mall.service.BaseCustomerInfoService;
import com.example.mall.utils.Result;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

/**
 * 客户信息表(BaseCustomerInfo)表控制层
 *
 * @author tang
 * @since 2022-03-14 10:03:01
 */
@RestController
@RequestMapping("customerInfo")
public class BaseCustomerInfoController {
  /** 服务对象 */
  @Resource private BaseCustomerInfoService baseCustomerInfoService;

  /**
   * 分页查询所有数据
   *
   * @param page 分页对象
   * @param baseCustomerInfo 查询实体
   * @return 所有数据
   */
  @GetMapping
  @PreAuthorize("hasAnyAuthority('admin', 'user')")
  public Result selectAll(Page<BaseCustomerInfo> page, BaseCustomerInfo baseCustomerInfo) {
    return Result.of(this.baseCustomerInfoService.page(page, new QueryWrapper<>(baseCustomerInfo)));
  }

  /**
   * 通过主键查询单条数据
   *
   * @param id 主键
   * @return 单条数据
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAnyAuthority('admin')")
  public Result selectOne(@PathVariable Serializable id) {
    return Result.of(this.baseCustomerInfoService.getById(id));
  }

  /**
   * 新增数据
   *
   * @param baseCustomerInfo 实体对象
   * @return 新增结果
   */
  @PostMapping
  @PreAuthorize("hasAnyAuthority('admin', 'user')")
  public Result insert(@RequestBody BaseCustomerInfo baseCustomerInfo) {
    return Result.of(this.baseCustomerInfoService.save(baseCustomerInfo));
  }

  /**
   * 修改数据
   *
   * @param baseCustomerInfo 实体对象
   * @return 修改结果
   */
  @PutMapping
  @PreAuthorize("hasAnyAuthority('admin', 'user')")
  public Result update(@RequestBody BaseCustomerInfo baseCustomerInfo) {
    return Result.of(this.baseCustomerInfoService.updateById(baseCustomerInfo));
  }

  /**
   * 删除数据
   *
   * @param idList 主键结合
   * @return 删除结果
   */
  @DeleteMapping
  @PreAuthorize("hasAnyAuthority('admin', 'user')")
  public Result delete(@RequestParam("idList") List<Long> idList) {
    return Result.of(this.baseCustomerInfoService.removeByIds(idList));
  }
}
