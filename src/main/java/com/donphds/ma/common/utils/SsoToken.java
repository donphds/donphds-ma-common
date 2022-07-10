package com.donphds.ma.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.donphds.ma.common.pojo.po.DomainUser;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: donphds
 **/
@Setter
public class SsoToken extends Token {
  private List<String> openIds;

  public SsoToken() {
    super();
  }

  public SsoToken(Token token) {
    super(token);
  }

  public void removeIf(String openId) {
    if (CollectionUtils.isEmpty(openIds)) {
      return;
    }
    openIds.remove(openId);
  }

  public void putIf(String openId) {
    if (!openIds.contains(openId)) {
      openIds.add(openId);
    }
  }

  public static SsoToken create(DomainUser subject) {
    SsoToken ssoToken = new SsoToken(Factory.build(new Issuer()).sub(subject));
    if (ssoToken.openIds == null) {
      ssoToken.openIds = new ArrayList<>(3);
    }
    ssoToken.putIf(subject.getOpenId());
    ssoToken.id(ssoToken.openIds);
    return ssoToken;
  }

  public SsoToken newSsoToken(DomainUser subject, String audience) {
    putIf(subject.getOpenId());
    return new SsoToken(Factory.build(new Issuer())
      .id(this.openIds).sub(subject).aud(audience));
  }

  public SsoToken newSsoToken(DomainUser subject, String audience, Integer expIn) {
    putIf(subject.getOpenId());
    return new SsoToken(Factory.build(new Issuer())
      .id(this.openIds).sub(subject).aud(audience).exp(expIn));
  }

  public String toJwt(Key key) {
    return this.toJwt(key, BeanMap.create(this), SignatureAlgorithm.RS256);
  }

}
