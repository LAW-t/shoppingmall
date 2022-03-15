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
  /** 未知原因 */
  UNKNOWN_REASON(false, 20001, "未知错误"),
  /** sql语法错误 */
  BAD_SQL_GRAMMAR(false, 21001, "sql语法错误"),
  /** json解析错误 */
  JSON_PARSE_ERROR(false, 21002, "json解析异常"),
  /** 参数错误 */
  PARAM_ERROR(false, 21003, "参数不正确"),
  /** 文件上传错误 */
  FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
  /** excel数据导入错误 */
  EXCEL_DATA_IMPORT_ERROR(false, 21005, "Excel数据导入错误");

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
