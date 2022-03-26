package com.example.mall.utils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: make mpy @Description: 获取IP的方法 @Date: 2021/1/18 15:02
 */
@Slf4j
public class IpUtil {
  private static final String LOCAL_IP = "127.0.0.1";
  /**
   * 获取IP地址
   *
   * <p>使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
   * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
   */
  public static String getIpAddr(HttpServletRequest request) {
    if (request == null) {
      return "unknown";
    }
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }

    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_IP : ip;
  }

  public static boolean internalIp(String ip) {
    boolean res = false;
    byte[] addr = textToNumericFormatV4(ip);
    if (addr != null && ip != null) {
      res = internalIp(addr) || LOCAL_IP.equals(ip);
    }
    return res;
  }

  private static boolean internalIp(byte[] addr) {
    final byte b0 = addr[0];
    final byte b1 = addr[1];
    // 10.x.x.x/8
    final byte SECTION_1 = 0x0A;
    // 172.16.x.x/12
    final byte SECTION_2 = (byte) 0xAC;
    final byte SECTION_3 = (byte) 0x10;
    final byte SECTION_4 = (byte) 0x1F;
    // 192.168.x.x/16
    final byte SECTION_5 = (byte) 0xC0;
    final byte SECTION_6 = (byte) 0xA8;
    boolean flag = false;
    switch (b0) {
      case SECTION_1:
        flag = true;
        break;
      case SECTION_2:
        if (b1 >= SECTION_3 && b1 <= SECTION_4) {
          flag = true;
        }
        break;
      case SECTION_5:
        if (b1 == SECTION_6) {
          flag = true;
        }
        break;
      default:
        break;
    }
    return flag;
  }

  /**
   * 将IPv4地址转换成字节 IPv4地址
   *
   * @return byte 字节
   */
  public static byte[] textToNumericFormatV4(String text) {
    if (text.length() == 0) {
      return new byte[0];
    }

    byte[] bytes = new byte[4];
    String[] elements = text.split("\\.", -1);
    try {
      long l;
      int i;
      switch (elements.length) {
        case 1:
          l = Long.parseLong(elements[0]);
          if ((l < 0L) || (l > 4294967295L)) {
            return new byte[0];
          }
          bytes[0] = (byte) (int) (l >> 24 & 0xFF);
          bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
          bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
          bytes[3] = (byte) (int) (l & 0xFF);
          break;
        case 2:
          l = Integer.parseInt(elements[0]);
          if ((l < 0L) || (l > 255L)) {
            return new byte[0];
          }
          bytes[0] = (byte) (int) (l & 0xFF);
          l = Integer.parseInt(elements[1]);
          if ((l < 0L) || (l > 16777215L)) {
            return new byte[0];
          }
          bytes[1] = (byte) (int) (l >> 16 & 0xFF);
          bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
          bytes[3] = (byte) (int) (l & 0xFF);
          break;
        case 3:
          for (i = 0; i < 2; ++i) {
            l = Integer.parseInt(elements[i]);
            if ((l < 0L) || (l > 255L)) {
              return new byte[0];
            }
            bytes[i] = (byte) (int) (l & 0xFF);
          }
          l = Integer.parseInt(elements[2]);
          if ((l < 0L) || (l > 65535L)) {
            return new byte[0];
          }
          bytes[2] = (byte) (int) (l >> 8 & 0xFF);
          bytes[3] = (byte) (int) (l & 0xFF);
          break;
        case 4:
          for (i = 0; i < 4; ++i) {
            l = Integer.parseInt(elements[i]);
            if ((l < 0L) || (l > 255L)) {
              return new byte[0];
            }
            bytes[i] = (byte) (int) (l & 0xFF);
          }
          break;
        default:
          return new byte[0];
      }
    } catch (NumberFormatException e) {
      log.error("数字格式化异常", e);
      return new byte[0];
    }
    return bytes;
  }

  public static String getLocalIP() {
    String ip = "";
    if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
      InetAddress addr;
      try {
        addr = InetAddress.getLocalHost();
        ip = addr.getHostAddress();
      } catch (UnknownHostException e) {
        log.error("获取失败", e);
      }
      return ip;
    } else {
      try {
        Enumeration<?> e1 = NetworkInterface.getNetworkInterfaces();
        while (e1.hasMoreElements()) {
          NetworkInterface ni = (NetworkInterface) e1.nextElement();
          if (!"eth0".equals(ni.getName())) {
            continue;
          } else {
            Enumeration<?> e2 = ni.getInetAddresses();
            while (e2.hasMoreElements()) {
              InetAddress ia = (InetAddress) e2.nextElement();
              if (ia instanceof Inet6Address) {
                continue;
              }
              ip = ia.getHostAddress();
              return ip;
            }
            break;
          }
        }
      } catch (SocketException e) {
        log.error("获取失败", e);
      }
    }
    return "";
  }

  /**
   * 把字符串IP转换成long
   *
   * @param ipStr 字符串IP
   * @return IP对应的long值
   */
  public static long ip2Long(String ipStr) {
    String[] ip = ipStr.split("\\.");
    return (Long.parseLong(ip[0]) << 24)
        + (Long.parseLong(ip[1]) << 16)
        + (Long.parseLong(ip[2]) << 8)
        + Long.parseLong(ip[3]);
  }

  /**
   * 把IP的long值转换成字符串
   *
   * @param ipLong IP的long值
   * @return long值对应的字符串
   */
  public static String long2Ip(long ipLong) {
    return (ipLong >>> 24)
        + "."
        + ((ipLong >>> 16) & 0xFF)
        + "."
        + ((ipLong >>> 8) & 0xFF)
        + "."
        + (ipLong & 0xFF);
  }
}
