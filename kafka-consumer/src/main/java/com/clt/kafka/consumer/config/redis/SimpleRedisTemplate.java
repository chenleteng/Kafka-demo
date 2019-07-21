package com.clt.kafka.consumer.config.redis;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleRedisTemplate {

    private final static String DEFAULT_CHARSET = "UTF-8";
    private RedisDatabase.JedisResource jedisResource;

    public SimpleRedisTemplate(RedisDatabase.JedisResource jedisResource) {
        this.jedisResource = jedisResource;
    }

    public boolean exists(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.exists(key.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long increase(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.incr(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long increase(String key, long val) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.incrBy(key, val);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long decrease(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.decr(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long decrease(String key, long val) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.decrBy(key, val);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public boolean hexists(String key, String field) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hexists(key.getBytes(DEFAULT_CHARSET), field.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public byte[] get(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.get(key.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public byte[] get(String key, String field) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hget(key.getBytes(DEFAULT_CHARSET), field.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String getSet(String key, String value) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.getSet(key, value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void put(String key, byte[] value) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.set(key.getBytes(DEFAULT_CHARSET), value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void put(String key, String value) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void put(String key, String field, byte[] value) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.hset(key.getBytes(DEFAULT_CHARSET), field.getBytes(DEFAULT_CHARSET), value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void put(String key, byte[] value, int expirySeconds) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.set(key.getBytes(DEFAULT_CHARSET), value);
            jedis.expire(key.getBytes(DEFAULT_CHARSET), expirySeconds);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long expire(String key, int seconds) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public boolean remove(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.del(key.getBytes(DEFAULT_CHARSET));
            return true;
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long delete(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.del(key.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long delete(String... keys) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.del(keys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public boolean remove(String... keys) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.del(keys);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public boolean remove(String key, String field) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.hdel(key.getBytes(DEFAULT_CHARSET), field.getBytes(DEFAULT_CHARSET));
            return true;
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long sadd(String key, String member) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.sadd(key, member);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void srem(String key, String member) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.srem(key, member);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> smembers(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.smembers(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public boolean sismember(String key, String member) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.sismember(key, member);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long scard(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.scard(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Map<String, String> hgetall(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hgetAll(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String hget(String key, String field) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void hmset(String key, Map<String, String> kvmap) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.hmset(key, kvmap);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }

    }

    public void hdel(String key, Set<String> fields) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            for (String field : fields) {
                jedis.hdel(key, field);
            }
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }

    }

    public void hdel(String key, String field) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.hdel(key, field);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }

    }

    public Set<String> hkeys(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hkeys(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long hincrby(String key, String field, long value) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }

    }

    public long zadd(String key, double score, String member) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zadd(key, score, member);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long zcard(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zcard(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long hset(String key, String field, String value) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<Tuple> zrangeWithScores(String key, int start, int end) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrangeWithScores(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> zrevrange(String key, int start, int end) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<Tuple> zrevrangeWithScores(String key, int start, int end) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrevrangeWithScores(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> zrevrangeByScore(String key, double min, double max) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrevrangeByScore(key, max, min);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long zremrangeByRank(String key, int start, int end) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long zrem(String key, String member) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrem(key, member);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Double zscore(String key, String member) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long hincrby(String key, String field, long value, long expirySeconds) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            long amount = jedis.hincrBy(key, field, value);
            jedis.expireAt(key, expirySeconds);
            return amount;
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }

    }

    public void put(String key, String value, long expirySeconds) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            jedis.set(key, value);
            jedis.expireAt(key, expirySeconds);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }

    }

    public void expireAt(String key, long expirySeconds) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            jedis.expireAt(key, expirySeconds);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = this.jedisResource.getJedis();

        try {

            return jedis.keys(pattern);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }

    }

    public String setString(String key, String value) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.set(key, value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String setString(String key, String value, int expirySeconds) {
        Jedis jedis = this.jedisResource.getJedis();

        try {

            String s = jedis.set(key, value);
            jedis.expire(key, expirySeconds);
            return s;
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String getString(String key) {
        Jedis jedis = this.jedisResource.getJedis();

        try {

            return jedis.get(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void lpush(String key, String val, long expirySeconds) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            jedis.lpush(key, val);
            jedis.expireAt(key, expirySeconds);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Long lpush(String key, String val) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.lpush(key, val);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Long rpush(String key, String val) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.rpush(key, val);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String lpop(String key) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.lpop(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String lindex(String key, int index) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.lindex(key, index);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Long lrem(String key, long count, String value) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.lrem(key, count, value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = this.jedisResource.getJedis();

        try {

            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long llen(String key) {
        Jedis jedis = this.jedisResource.getJedis();

        try {

            return jedis.llen(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long publish(String channel, String message) {
        Jedis jedis = this.jedisResource.getJedis();

        try {

            return jedis.publish(channel, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void subscribe(JedisPubSub jedisPubSub, String channels) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            jedis.subscribe(jedisPubSub, channels);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Double zincrby(String key, double score, String member) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<String> sinter(String... keys) {
        Jedis jedis = this.jedisResource.getJedis();

        try {
            return jedis.sinter(keys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Long zcount(String key, double min, double max) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zcount(key, min, max);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String info() {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.info();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Long ttl(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.ttl(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Long persist(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.persist(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String spop(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.spop(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public Long hlen(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.hlen(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public String randomkey() {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.randomKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    /**
     * 随机获取集合中的元素
     */
    public String srandmember(String key) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.srandmember(key);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void ltrim(String key, int start, int end) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.ltrim(key, start, end);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public void zunionstore(String dstkey, String... sets) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            jedis.zunionstore(dstkey, sets);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

    public long setnx(String key, String value) {
        Jedis jedis = this.jedisResource.getJedis();
        try {
            return jedis.setnx(key, value);
        } catch (Exception e) {
            throw new RuntimeException("key=" + key + ",message=" + e.getMessage(), e);
        } finally {
            this.jedisResource.close(jedis);
        }
    }

}
