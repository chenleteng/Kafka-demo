package com.clt.kafka.consumer.config;

import com.qw.support.springboot.common.utils.ParamVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParamVerifierConfig {

    @Bean
    public ParamVerifier paramVerifier() {
        return new ParamVerifier();
    }

}
