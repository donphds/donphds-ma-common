package com.donphds.ma.common.pojo.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: donphds
 **/
@Data
public class DomainUser implements Serializable {
  private String openid;
  private String name;
  private String avatar;
}
