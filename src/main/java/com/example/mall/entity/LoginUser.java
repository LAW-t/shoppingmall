package com.example.mall.entity;

import com.alibaba.fastjson.annotation.JSONField;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实现{@link org.springframework.security.core.userdetails.UserDetails}
 *
 * @author 汤卫豪
 * @version V1.0
 * @date 2022/3/14 14:30
 */
@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {
  private static final long serialVersionUID = 4939048127892732523L;

  private BaseUser user;
  private List<String> authorities;

  @JSONField(serialize = false)
  private List<SimpleGrantedAuthority> grantedAuthorities;

  public LoginUser(BaseUser user, List<String> authorities) {
    this.user = user;
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.grantedAuthorities == null) {
      this.grantedAuthorities =
          this.authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    return this.grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getPhone();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
