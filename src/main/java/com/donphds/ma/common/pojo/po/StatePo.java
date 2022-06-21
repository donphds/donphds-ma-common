package com.donphds.ma.donphdsmaauth.pojo.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Data
public class StatePo {
    private String state;
    @JsonProperty("redirect_url")
    private String redirectUrl;
}
