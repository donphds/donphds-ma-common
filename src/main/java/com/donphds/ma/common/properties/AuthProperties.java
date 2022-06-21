package com.donphds.ma.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: donphds
 * @Since: 1.0
 **/
@Data
@ConfigurationProperties("auth")
public class AuthProperties {
    private String scheme;
    private String state;
    private String fmt;
}
