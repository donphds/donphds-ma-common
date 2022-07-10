package com.donphds.ma.common.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.donphds.ma.common.constants.CommonConstants;
import com.donphds.ma.common.pojo.po.DomainUser;
import com.google.common.base.Joiner;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: donphds
 **/

@Getter
@NoArgsConstructor
public class Token {
  /**
   * @param aud                受理人
   * @param iss                委托人
   * @param jti                唯一id
   * @param sub                主体信息
   * @param exp                过期时间
   */
  private String id;
  private String aud;
  private String sub;
  private String exp;
  private String iss;

  public Token(Token token) {
    this.id = token.id;
    this.aud = token.aud;
    this.sub = token.sub;
    this.exp = token.exp;
    this.iss = token.iss;
  }

  protected Token id(String id) {
    this.id = id;
    return this;
  }

  protected Token id(List<String> ids) {
    return id(Joiner.on(".").skipNulls().join(ids));
  }

  protected Token aud(String aud) {
    this.aud = aud;
    return this;
  }

  protected Token sub(String sub) {
    this.sub = sub;
    return this;
  }

  protected Token sub(DomainUser domainUser) {
    return sub(domainUser.getName() + CommonConstants.JOINER_SPLIT + domainUser.getAvatar());
  }


  public String toJwt(
    Key key,
    Map<String, Object> claims,
    SignatureAlgorithm signatureAlgorithm) {
    return Jwts.builder()
      .signWith(key, signatureAlgorithm)
      .addClaims(CollectionUtils.isEmpty(claims) ? Map.of() : claims)
      .compact();
  }

  /**
   * @param token jwt
   * @param key   asy 为公钥
   * @return
   */
  public Map<String, Object> getClaimsMap(String token, Key key) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token).getBody();
  }

  public Claims getClaims(String token, Key key) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token).getBody();
  }

  public Boolean isExpired(String token, Key key) {
    return getClaims(token, key).getExpiration().before(new Date());
  }

  public  Boolean verify(String token, Key key) {
    try {
      return Objects.isNull(getClaimsMap(token, key)) ? Boolean.FALSE : Boolean.TRUE;
    } catch (Exception e) {
      return Boolean.FALSE;
    }
  }

  protected Token exp(Integer expIn) {
    Calendar instance = Calendar.getInstance();
    instance.set(Calendar.HOUR, expIn);
    this.exp = DateUtil.format(instance.getTime(), DatePattern.NORM_DATETIME_PATTERN);
    ;
    return this;
  }

  protected static class Factory {
    private Factory(Issuer issuer) {
      this.issuer = issuer;
    }

    public static Token build(Issuer issuer) {
      Factory factory = new Factory(issuer);
      Token token = new Token();
      token.iss = issuer.getIss();
      return token;
    }
    public Issuer issuer;

  }

}
