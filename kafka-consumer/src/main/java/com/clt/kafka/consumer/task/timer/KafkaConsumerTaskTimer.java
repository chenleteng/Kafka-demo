package com.clt.kafka.consumer.task.timer;

import com.alibaba.fastjson.JSON;
import com.clt.kafka.consumer.dto.EventOffset;
import com.clt.kafka.consumer.log.LogFactory;
import com.clt.kafka.consumer.server.EventService;
import com.clt.kafka.consumer.task.executors.EventBlockExecutor;
import com.clt.kafka.consumer.util.ZipUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;

@Component
public class KafkaConsumerTaskTimer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Logger kafkaConsumerLogger = LogFactory.getLogger(LogFactory.LOGGER.KafkaConsumerLogger);

    @Resource
    private EventBlockExecutor eventBlockExecutor;
    @Resource
    private EventService eventService;

    @Resource
    private Map<String, Object> consumerConfigs;

    @Resource
    private KafkaConsumer<byte[], byte[]> kafkaConsumer;

    private boolean stop = false;
    private Thread thread;

    @PreDestroy
    public void stop() {
        logger.info("感知到主程序终止。");
        stop = true;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void start() {
        thread = new Thread(new ConsumerTaskRunnable());
        thread.setDaemon(false);
        thread.start();
    }

    private class ConsumerTaskRunnable implements Runnable {

        private String consumerId;
        private List<String> topics = new ArrayList<>();
        private int pullTimeoutMs;

        private void init() {

            for (String t : consumerConfigs.get("_topics").toString().split(",")) {
                topics.add(t.trim());
            }

            this.consumerId = consumerConfigs.get(ConsumerConfig.GROUP_ID_CONFIG).toString();
            this.pullTimeoutMs = Integer.parseInt(consumerConfigs.get("_pullTimeoutMs").toString());

            kafkaConsumer.subscribe(topics, new ConsumerRebalanceListener() {

                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                    //所有partition一起更新入库
                    List<EventOffset> offsetList = new ArrayList<>();
                    collection.forEach(partition -> {
                        EventOffset offset = new EventOffset();
                        offset.setTopic(partition.topic());
                        offset.setPartition(partition.partition());
                        offset.setOffset(kafkaConsumer.position(partition));
                        offset.setConsumerId(consumerId);
                        //
                        offsetList.add(offset);
                        logger.info("onPartitionsRevoked, offset={}", JSON.toJSONString(offset));
                    });
                    if (offsetList.size() > 0) offsetList.forEach(ol -> {
//                        eventService.saveOffset(ol);
                    });
                }

                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                    seek(collection, "assigned");
                }
            });
            //kafkaConsumer.poll(Duration.ofMillis(this.pullTimeoutMs));
            //kafkaConsumer.poll(this.pullTimeoutMs);
            //seek(kafkaConsumer.assignment(), "init");
        }

        private void seek(Collection<TopicPartition> collection, final String from) {
            collection.forEach(partition -> {
                EventOffset offset = eventService.getOffset(
                        consumerId,
                        partition.topic(), partition.partition());
                if (offset != null) {
                    if (offset.getOffset() == null) {
                        logger.info("定位Kafka位置offset, {} seekToEnd, topic={},partition={}", from, partition.topic(), partition.partition());
                        //kafkaConsumer.seekToEnd(Collections.singleton(partition));
                        kafkaConsumer.seekToEnd(Arrays.asList(partition));
                    } else {
                        logger.info("定位Kafka位置offset, {} seek, offset={}", from, JSON.toJSONString(offset));
                        kafkaConsumer.seek(partition, offset.getOffset());
                    }
                } else {
                    logger.info("null 定位Kafka位置offset, {} seekToEnd, topic={},partition={}", from, partition.topic(), partition.partition());
                    //kafkaConsumer.seekToEnd(Collections.singleton(partition));
                    kafkaConsumer.seekToEnd(Arrays.asList(partition));
                }
            });
        }

        private long sleepTime = 0;

        @Override
        public void run() {
            logger.info("消费任务开启了。");
            init();
            while (!stop) try {
                consume();
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    //ignore
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            logger.info("优雅的结束。");
        }

        private void consume() {

            List<EventOffset> offsets = new ArrayList<>();
            //ConsumerRecords<byte[], byte[]> records = kafkaConsumer.poll(Duration.ofMillis(pullTimeoutMs));
            ConsumerRecords<byte[], byte[]> records = kafkaConsumer.poll(pullTimeoutMs);

            sleepTime = records.isEmpty() ? pullTimeoutMs : 10;

            records.partitions().forEach(partition -> {
                List<ConsumerRecord<byte[], byte[]>> partitionRecords = records.records(partition);
                logger.debug("消费到{}条数据。", partitionRecords.size());
                partitionRecords.forEach(record -> {
                    try {

                        byte[] value = record.value();
                        String data = new String(ZipUtils.unzip(value));
                        if (value.length > 20) {
                            kafkaConsumerLogger.info("消费到数据:{},topic={},partition={},offset={}", value, record.topic(), record.partition(), record.offset());
                            eventBlockExecutor.doEvent(data, record.topic());
                        } else {
                            kafkaConsumerLogger.info("消费数据失败:{},topic={},partition={},offset={}", data, record.topic(), record.partition(), record.offset());
                        }

                    } catch (Exception e) {
                        logger.error("处理异常。", e);
                    }
                });

                long lastOffsetPosition = partitionRecords.get(partitionRecords.size() - 1).offset() + 1;
                kafkaConsumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffsetPosition)));

                EventOffset offset = new EventOffset();
                offset.setConsumerId(consumerId);
                offset.setTopic(partition.topic());
                offset.setPartition(partition.partition());
                offset.setOffset(lastOffsetPosition);
                offsets.add(offset);

            });

            if (offsets.size() > 0) offsets.forEach(o -> {
                eventService.saveOffset(o);
            });

        }

    }

}
