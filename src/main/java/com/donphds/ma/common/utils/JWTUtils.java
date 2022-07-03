package com.donphds.ma.common.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JWTUtils {


  /**
   * @param aud                受理人
   * @param iss                委托人
   * @param jti                唯一id
   * @param sub                主体信息
   * @param exp                过期时间
   * @param key                asy 时为私钥
   * @param claims             附带信息，会覆盖
   * @param signatureAlgorithm 签名算法
   * @return
   */
  public static String create(
    String aud,
    String iss,
    String jti,
    String sub,
    Integer exp,
    Key key,
    Map<String, Object> claims,
    SignatureAlgorithm signatureAlgorithm) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, exp);
    return Jwts.builder()
      .signWith(key, signatureAlgorithm)
      .addClaims(CollectionUtils.isEmpty(claims) ? Map.of() : claims)
      .setId(jti)
      .setAudience(aud)
      .setIssuer(iss)
      .setIssuedAt(new Date())
      .setExpiration(calendar.getTime())
      .setSubject(sub).compact();
  }

  /**
   * @param token jwt
   * @param key   asy 为公钥
   * @return
   */

  public static Map<String, Object> getClaimsMap(String token, Key key) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token).getBody();
  }

  public static Claims getClaims(String token, Key key) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token).getBody();
  }

  public static Boolean isExpired(String token, Key key) {
    return getClaims(token, key).getExpiration().before(new Date());
  }

  public static Boolean verify(String token, Key key) {
    try {
      return Objects.isNull(getClaimsMap(token, key)) ? Boolean.FALSE : Boolean.TRUE;
    } catch (Exception e) {
      log.error("jwt 验证失败: {}", token);
      return Boolean.FALSE;
    }
  }
}
