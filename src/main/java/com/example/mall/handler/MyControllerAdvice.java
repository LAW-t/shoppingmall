package com.example.mall.handler;

import com.example.mall.utils.Result;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

/**
 * 异常处理器
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/15 10:28
 */
@ControllerAdvice
@Log4j2
public class MyControllerAdvice {

  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public Result exceptionHandler(Exception e) {
    // 获取异常信息，存放如ResponseResult的msg属性
    String message = e.getMessage();
    log.error("异常信息：{}", message);
    Result result = new Result();
    // 把ResponseResult作为返回值返回，要求到时候转换成json存入响应体中
    return result.success(false).code(500).message(message).data(null);
  }
}
