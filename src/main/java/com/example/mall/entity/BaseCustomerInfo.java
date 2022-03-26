package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 客户信息表(BaseCustomerInfo)表实体类
 *
 * @author makejava
 * @since 2022-03-26 15:18:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("base_customer_info")
public class BaseCustomerInfo extends Model<BaseCustomerInfo> {
  private static final long serialVersionUID = -519356144554648802L;
  /** 编号 * */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;
  /** 客户信息类型（0立刻付款，1按月付款） * */
  private Integer type;
  /** 联系电话 * */
  private String phone;
  /** 联系人的真实姓名 * */
  private String realName;
  /** 地址-省 * */
  private String addrProvince;
  /** 地址-市 * */
  private String addrCity;
  /** 地址-区 * */
  private String addrDistrict;
  /** 详细地址 * */
  private String addrDetailed;
  /** 备注 * */
  private String remark;
  /** 邮箱 * */
  private String email;
  /** 创建时间 * */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
  /** 更新时间 * */
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;
  /** 删除标记 * */
  private Integer delFlag;
  /** 版本号 * */
  @Version private Integer version;

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
