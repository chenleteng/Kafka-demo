package com.clt.kafka.consumer.config.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConditionalOnProperty(name = {"redis.enable"})
public class JedisConfig extends RedisPoolProperties {

    public JedisPool getJedisPool(String host, int port, String password, int database, int timeout) {

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxIdle(getPoolMaxIdle());
        poolConfig.setMinIdle(getPoolMinIdle());
        poolConfig.setMaxTotal(getPoolMaxActive());
        poolConfig.setMaxWaitMillis(getPoolMaxWait());
        poolConfig.setTestOnBorrow(true);

        return new JedisPool(poolConfig, host, port, timeout, password == null || password.length() == 0 ? null : password, database);

    }

}
