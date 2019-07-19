package com.clt.kafka.provider.service;

import com.alibaba.fastjson.JSONObject;
import com.clt.kafka.provider.util.ZipUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class KafkaSenderService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaSenderService.class);

    @Resource
    private KafkaProducer<byte[], byte[]> kafkaProducer;

    public void sendEvent(JSONObject param) {
        String jsonString = param.toJSONString();
        try {
            byte[] data = ZipUtils.zip(jsonString.getBytes());
            kafkaProducer.send(new ProducerRecord<>("clt_test", data), (recordMetadata, e) -> {
                if (recordMetadata != null) {
                    logger.info("发送成功。topic={}, jsonString={}", "clt_test", jsonString);
                } else {
                    logger.error("发送失败！topic={}, jsonString={}", "clt_test", jsonString);
                    logger.error(e.getMessage(), e);
                }
            });
        } catch (Exception e) {
            logger.error("发送失败！topic={}, jsonString={}", "clt_test", jsonString);
            logger.error(e.getMessage(), e);
        }
    }

}
