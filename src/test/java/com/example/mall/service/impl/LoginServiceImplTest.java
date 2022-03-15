package com.example.mall.service.impl;

import junit.framework.TestCase;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginServiceImplTest extends TestCase {

  public void testLogin() {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String password = encoder.encode("123");
    System.out.println(password);
  }
}
