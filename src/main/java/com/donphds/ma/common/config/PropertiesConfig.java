package com.donphds.ma.common.config;

import com.donphds.ma.common.properties.CommonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
@EnableConfigurationProperties({
        CommonProperties.class,
})
public class PropertiesConfig {
}
