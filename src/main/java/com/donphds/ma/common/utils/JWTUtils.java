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
      ;
      return Objects.isNull(getClaimsMap(token, key)) ? Boolean.FALSE : Boolean.TRUE;
    } catch (Exception e) {
      log.error("jwt 验证失败: {}", token);
      return Boolean.FALSE;
    }
  }
}
