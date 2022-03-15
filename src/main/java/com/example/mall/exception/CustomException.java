package com.example.mall.exception;

/**
 * 业务异常类
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/15 10:22
 */
public class CustomException extends RuntimeException {
  private static final long serialVersionUID = 8007041155461330430L;

  public CustomException(String message) {
    super(message);
  }
}
