package com.clt.kafka.consumer.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogFactory {
    private static final Map<LOGGER, Logger> container = new ConcurrentHashMap<>();

    public enum LOGGER {
        UserRegisterLogger,
        UserLoginLogger,
        KafkaConsumerLogger
    }

    static {
        for (LOGGER log : LOGGER.values()) {
            container.put(log, LoggerFactory.getLogger(log.name()));
        }
    }

    public static Logger getLogger(LOGGER logger) {
        return container.get(logger);
    }
}
