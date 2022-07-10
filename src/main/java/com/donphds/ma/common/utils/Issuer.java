package com.donphds.ma.common.utils;

/**
 * @Author: donphds
 **/
public class Issuer {
  private String iss;

  private String basePrefix() {
    return "httP://tplentiful.bio/";
  }

  private String baseUri() {
    return "login";
  }

  public String baseUri(String uri) {
    return uri;
  }

  public String getIss() {
    return basePrefix() + baseUri();
  }
}
