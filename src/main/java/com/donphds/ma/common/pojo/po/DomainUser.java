package com.donphds.ma.common.pojo.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: donphds
 **/
@Data
public class DomainUser implements Serializable {
  @JsonProperty("open_id")
  private String openId;
  private String name;
  private String avatar;
}
