package com.example.mall.vo;

import lombok.Data;

/**
 * 用户信息的vo类
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/31 20:49
 */
@Data
public class UserInfoVo {
  /** 编号 * */
  private Long id;
  /** 用户手机号 * */
  private String phone;
  /** 密码 */
  private String password;
  /** 微信小程序openid。默认为空 * */
  private String openid;
  /** 用户类型（0管理员，1客户） * */
  private Integer type;
  /** 备注 * */
  private String remark;
  /** 昵称 * */
  private String nickname;
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
  /** 邮箱 * */
  private String email;
  /** token */
  private String token;
}
