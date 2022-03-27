package com.example.mall.utils;

import com.example.mall.enums.ResultCodeEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回的数据格式
 *
 * @author 汤卫豪
 * @version V1.0
 * @className com.ssm.util.Result
 * @date 2022/01/16
 */
@Data
@NoArgsConstructor
public class Result {
  private Integer code;
  private String message;
  private Boolean success;
  private Object data;

  public static Result of(Object data) {
    if (data == null || Boolean.FALSE.equals(data)) {
      return new Result()
          .success(ResultCodeEnum.UNKNOWN_REASON.isSuccess())
          .code(ResultCodeEnum.UNKNOWN_REASON.getCode())
          .message(ResultCodeEnum.UNKNOWN_REASON.getMessage())
          .data(null);
    } else {
      return new Result()
          .success(ResultCodeEnum.SUCCESS.isSuccess())
          .code(ResultCodeEnum.SUCCESS.getCode())
          .message(ResultCodeEnum.SUCCESS.getMessage())
          .data(data);
    }
  }
  /**
   * 直接返回成功成功
   *
   * @param
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public static Result success() {
    Result result = new Result();
    result.setSuccess(ResultCodeEnum.SUCCESS.isSuccess());
    result.setCode(ResultCodeEnum.SUCCESS.getCode());
    result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
    return result;
  }

  /**
   * 直接返回失败
   *
   * @param
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public static Result error() {
    Result result = new Result();
    result.setSuccess(ResultCodeEnum.UNKNOWN_REASON.isSuccess());
    result.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
    result.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
    return result;
  }

  /**
   * 返回成功，并添加数据
   *
   * @param @param data 数据
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public static Result success(Object data) {
    Result result = new Result();
    result.setSuccess(ResultCodeEnum.SUCCESS.isSuccess());
    result.setCode(ResultCodeEnum.SUCCESS.getCode());
    result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
    result.setData(data);
    return result;
  }

  /**
   * 返回结果可能出现的结果
   *
   * @param @param resultCodeEnum 枚举结果代码
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public static Result setResult(ResultCodeEnum resultCodeEnum) {
    Result result = new Result();
    result.setSuccess(resultCodeEnum.isSuccess());
    result.setCode(resultCodeEnum.getCode());
    result.setMessage(resultCodeEnum.getMessage());
    return result;
  }

  /**
   * 手动设置成功状态
   *
   * @param @param success 成功
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public Result success(Boolean success) {
    this.setSuccess(success);
    return this;
  }

  /**
   * 手动设置消息体
   *
   * @param @param message 消息
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public Result message(String message) {
    this.setMessage(message);
    return this;
  }

  /**
   * 手动设置状态码
   *
   * @param @param code 代码
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public Result code(Integer code) {
    this.setCode(code);
    return this;
  }

  /**
   * 手动设置数据体
   *
   * @param @param data 数据
   * @return @return {@link Result }
   * @throws
   * @since 2022/01/29
   */
  public Result data(Object data) {
    this.data = data;
    return this;
  }
}
