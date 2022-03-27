package com.example.mall.enums;

import lombok.Getter;

/**
 * 结果代码
 *
 * @author 汤卫豪
 * @projectName ssm
 * @package com.ssm.enums @EnumName ResultCode
 * @date 2022/1/16 13:02
 */
@Getter
public enum ResultCodeEnum {
  /** 成功 */
  SUCCESS(true, 20000, "成功"),
  /** 失败 */
  FATAL(false, 50000, "失败"),
  /** 未知原因 */
  UNKNOWN_REASON(false, 50001, "未知错误"),
  /** 部分成功 */
  PARTIAL_SUCCESS(true, 20002, "部分成功"),
  /** 参数错误 */
  PARAM_ERROR(false, 40001, "参数错误"),
  /** 数据不存在 */
  DATA_NOT_EXIST(false, 40002, "数据不存在"),
  /** 数据已存在 */
  DATA_EXIST(false, 40003, "数据已存在"),
  /** 数据已被使用 */
  DATA_USED(false, 40004, "数据已被使用"),
  /** 数据已被其他数据依赖 */
  DATA_DEPENDENCY(false, 40005, "数据已被其他数据依赖"),
  /** 数据已被其他数据关联 */
  DATA_REFERENCE(false, 40006, "数据已被其他数据关联"),
  /** 数据已被其他数据关联 */
  DATA_REFERENCE_DELETE(false, 40007, "数据已被其他数据关联，无法删除"),
  /** 数据已被其他数据关联 */
  DATA_REFERENCE_UPDATE(false, 40008, "数据已被其他数据关联，无法更新"),
  /** sql语法错误 */
  BAD_SQL_GRAMMAR(false, 51001, "sql语法错误"),
  /** json解析错误 */
  JSON_PARSE_ERROR(false, 51002, "json解析异常"),
  /** 用户名或密码错误 */
  USERNAME_OR_PASSWORD_ERROR(false, 51003, "用户名或密码错误"),
  /** 用户名已被注册 */
  USERNAME_REGISTERED(false, 51004, "用户名已被注册"),
  /** 用户名不存在 */
  USERNAME_NOT_EXIST(false, 51005, "用户名不存在"),
  /** 用户名或密码错误 */
  USERNAME_OR_PASSWORD_ERROR_LOGIN(false, 51006, "用户名或密码错误"),
  /** 文件上传错误 */
  FILE_UPLOAD_ERROR(false, 51004, "文件上传错误"),
  /** excel数据导入错误 */
  EXCEL_DATA_IMPORT_ERROR(false, 51005, "Excel数据导入错误");

  private final boolean success;
  private final Integer code;
  private final String message;
  private Object data;

  ResultCodeEnum(Boolean success, Integer code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
  }
}
