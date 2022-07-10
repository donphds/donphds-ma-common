package com.donphds.ma.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: donphds
 **/
@Data
@ConfigurationProperties("common")
public class CommonProperties {
    private String scheme;
    private String state;
    private String fmt;
}
