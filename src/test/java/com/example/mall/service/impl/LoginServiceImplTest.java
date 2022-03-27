package com.example.mall.service.impl;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class LoginServiceImplTest extends TestCase {

  public void f(String... args) {
    System.out.println(Arrays.toString(args));
  }

  public void testLogin() {
    List<String> list = Arrays.asList("1", "2", "3");
    this.f(list.toArray(new String[0]));
  }
}
