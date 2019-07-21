package com.clt.kafka.consumer.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@ConfigurationProperties
public class RedisDatabase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String host;
    private int port;
    private String password;
    private int database;
    private int timeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public class JedisResource {

        private JedisPool jedisPool;

        public void setJedisPool(JedisPool jedisPool) {
            this.jedisPool = jedisPool;
        }

        public Jedis getJedis() {
            if (this.jedisPool == null) {
                throw new RuntimeException("Jedis pool is null!!!");
            }
            return this.jedisPool.getResource();
        }

        public void close(final Jedis jedis) {
            if (jedis != null) {
                try {
                    jedis.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

    }

    @Resource
    private JedisConfig jedisConfig;

    public JedisResource getJedisResource() {
        if (jedisConfig == null) throw new RuntimeException("注入JedisConfig失败！");
        JedisResource resource = new JedisResource();
        JedisPool jedisPool = jedisConfig.getJedisPool(getHost(), getPort(), getPassword(), getDatabase(), getTimeout());
        resource.setJedisPool(jedisPool);
        return resource;
    }

    public JedisResource getJedisResource(JedisConfig jedisConfig) {
        JedisResource resource = new JedisResource();
        JedisPool jedisPool = jedisConfig.getJedisPool(getHost(), getPort(), getPassword(), getDatabase(), getTimeout());
        resource.setJedisPool(jedisPool);
        return resource;
    }

}
