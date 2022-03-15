package com.example.mall.enums;

/**
 * 权限信息
 *
 * @author 汤卫豪
 * @date 2022/3/14 21:10
 */
public enum Authority {
  /** 管理员 */
  ADMIN("admin", 0),
  /** 普通用户 */
  USER("user", 1),
  /** 游客 */
  GUEST("guest", 2);

  private final String name;
  private final int value;

  Authority(String name, int value) {
    this.name = name;
    this.value = value;
  }

  public static Authority ofCode(Integer code) {
    for (Authority value : values()) {
      if (code == value.getValue()) {
        return value;
      }
    }
    return null;
  }

  public String getName() {
    return this.name;
  }

  public int getValue() {
    return this.value;
  }
}
