package com.example.mall.utils;

/**
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 22:19
 */
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class WebUtil {
  /**
   * 将字符串渲染到客户端
   *
   * @param response 渲染对象
   * @param string 待渲染的字符串
   * @return null
   */
  public static String renderString(HttpServletResponse response, String string) {
    try {
      response.setStatus(200);
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(string);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
