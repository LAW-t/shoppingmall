package com.example.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.annotations.UserInfo;
import com.example.mall.entity.BaseUser;
import com.example.mall.service.BaseUserService;
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
 * 用户表(BaseUser)表控制层
 *
 * @author tang
 * @since 2022-03-14 10:03:01
 */
@RestController
@RequestMapping("user")
public class BaseUserController {
  /** 服务对象 */
  @Resource private BaseUserService baseUserService;

  /**
   * 分页查询所有数据
   *
   * @param page 分页对象
   * @param baseUser 查询实体
   * @return 所有数据
   */
  @GetMapping
  public Result selectAll(Page<BaseUser> page, BaseUser baseUser) {
    return Result.of(this.baseUserService.page(page, new QueryWrapper<>(baseUser)));
  }

  /**
   * 通过主键查询单条数据
   *
   * @param id 主键
   * @return 单条数据
   */
  @GetMapping("{id}")
  @PreAuthorize("hasAuthority('admin')")
  public Result selectOne(@PathVariable Serializable id, @UserInfo BaseUser user) {
    return Result.of(user);
    //    return Result.of(this.baseUserService.getById(id));
  }

  /**
   * 新增数据
   *
   * @param baseUser 实体对象
   * @return 新增结果
   */
  @PostMapping
  public Result insert(@RequestBody BaseUser baseUser) {
    return Result.of(this.baseUserService.save(baseUser));
  }

  /**
   * 修改数据
   *
   * @param baseUser 实体对象
   * @return 修改结果
   */
  @PutMapping
  public Result update(@RequestBody BaseUser baseUser) {
    return Result.of(this.baseUserService.updateById(baseUser));
  }

  /**
   * 删除数据
   *
   * @param idList 主键结合
   * @return 删除结果
   */
  @DeleteMapping
  public Result delete(@RequestParam("idList") List<Long> idList) {
    return Result.of(this.baseUserService.removeByIds(idList));
  }
}
