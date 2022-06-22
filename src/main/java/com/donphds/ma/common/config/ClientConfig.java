package com.donphds.ma.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * @Author: tplentiful
 * @Since: 1.0
 **/
@Configuration
public class ClientConfig {

    @Bean
    public WebClient webClient() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> {
                    configurer.customCodecs().registerWithDefaultConfig(new Jackson2JsonDecoder());
                })
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .uriBuilderFactory(uriBuilderFactory)
                .build();
    }

}
