package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户表(BaseUser)表实体类
 *
 * @author makejava
 * @since 2022-03-26 15:18:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("base_user")
public class BaseUser extends Model<BaseUser> {
  private static final long serialVersionUID = 280098755402820485L;
  /** 编号 * */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  /** 用户手机号 * */
  private String phone;
  /** 加密后的密码 * */
  private String password;
  /** 微信小程序openid。默认为空 * */
  private String openid;
  /** 用户类型（0管理员，1客户） * */
  private Integer type;
  /** 备注 * */
  private String remark;
  /** 外键。关联客户信息表。0表示没有指向的外键 * */
  private Long customerInfoId;
  /** 昵称 * */
  private String nickname;
  /** 创建时间 * */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  /** 创建用户id * */
  private Long createBy;
  /** 更新用户id * */
  private Long updateBy;
  /** 删除标记（0表示未删除，1表示已删除） * */
  private Integer delFlag;
  /** 最后一次登录时间 * */
  private LocalDateTime lastLoginTime;
  /** ip地址 * */
  private Long lastLoginIp;

  /**
   * 获取主键值
   *
   * @return 主键值
   */
  @Override
  public Serializable pkVal() {
    return this.id;
  }
}
