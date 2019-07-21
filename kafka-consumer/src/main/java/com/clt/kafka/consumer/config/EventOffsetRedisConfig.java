package com.clt.kafka.consumer.config;

import com.clt.kafka.consumer.config.redis.RedisDatabase;
import com.clt.kafka.consumer.config.redis.SimpleRedisTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis.user")
public class EventOffsetRedisConfig extends RedisDatabase {

    @Bean("actRedisTemplate")
    public SimpleRedisTemplate simpleRedisTemplate() {
        return new SimpleRedisTemplate(getJedisResource());
    }

}
