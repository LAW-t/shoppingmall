package com.example.mall.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 允许访问
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/27 16:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "allow-access")
@Component
public class AllowAccess {
  private List<String> url;
}
