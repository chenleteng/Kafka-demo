package com.clt.kafka.consumer.server;

import com.alibaba.fastjson.JSON;
import com.clt.kafka.consumer.dto.EventOffset;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.zip.CRC32;

@Service
public class EventService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //private final static ThreadLocal<SimpleDateFormat> SDF = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

    private final static String EVENT_OFFSET_KEY = "OFFSET_%s_%s_%s";
    private final static String KEY_PREFIX = "EVENT_KEY-";
    private final static Integer ONE_DAY_SECONDS = 86400;

    private String getEventOffsetKey(String consumerId, String topic, Integer partition) {
        return String.format(EVENT_OFFSET_KEY, consumerId, topic, partition);
    }

//    @Resource
//    private SimpleRedisTemplate actRedisTemplate;

    private String getKey(String data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data.getBytes());
        return KEY_PREFIX + Long.toHexString(crc32.getValue()) + "-" + DigestUtils.md5Hex(data);
    }

//    public boolean exists(String data) {
//        return actRedisTemplate.exists(getKey(data));
//    }

//    public void cache(String data) {
//        actRedisTemplate.setString(getKey(data), "1", ONE_DAY_SECONDS);
//    }
//
//    public void saveOffset(EventOffset eventOffset) {
//        String key = getEventOffsetKey(eventOffset.getConsumerId(), eventOffset.getTopic(), eventOffset.getPartition());
//        actRedisTemplate.setString(key, JSON.toJSONString(eventOffset));
//    }
//
//    public EventOffset getOffset(String consumerId, String topic, Integer partition) {
//        String key = getEventOffsetKey(consumerId, topic, partition);
//        String val = actRedisTemplate.getString(key);
//        return val == null ? null : JSON.parseObject(val, EventOffset.class);
//    }

}
