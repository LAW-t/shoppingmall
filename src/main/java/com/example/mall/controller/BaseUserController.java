package com.example.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import javax.servlet.http.HttpServletRequest;

/**
 * 用户表(BaseUser)表控制层
 *
 * @author tang
 * @since 2022-03-25 19:25:33
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
  @PreAuthorize("hasAnyAuthority('admin')")
  public Result selectAll(Page<BaseUser> page, BaseUser baseUser) {
    return Result.of(this.baseUserService.getList(page, baseUser));
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
    return Result.of(this.baseUserService.getUserById(id));
  }

  /**
   * 新增管理员
   *
   * @param baseUser 实体对象
   * @return 新增结果
   */
  @PostMapping("register/admin")
  @PreAuthorize("hasAnyAuthority('admin')")
  public Result insertAdmin(@RequestBody BaseUser baseUser, HttpServletRequest request) {
    return Result.of(this.baseUserService.addAdmin(baseUser, request));
  }

  /**
   * 新增普通用户
   *
   * @param baseUser 实体对象
   * @return 新增结果
   */
  @PostMapping("register")
  public Result insertUser(@RequestBody BaseUser baseUser, HttpServletRequest request) {
    return Result.of(this.baseUserService.addUser(baseUser, request));
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
