package com.donphds.ma.common.config;

import com.donphds.ma.common.properties.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
@EnableConfigurationProperties({
        AuthProperties.class,
})
public class PropertiesConfig {
}
