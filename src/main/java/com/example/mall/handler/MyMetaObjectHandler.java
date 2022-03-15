package com.example.mall.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充处理器
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/15 16:01
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  @Override
  public void insertFill(MetaObject metaObject) {
    this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
    this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
  }
}
