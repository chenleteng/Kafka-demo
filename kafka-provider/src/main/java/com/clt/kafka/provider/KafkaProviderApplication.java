package com.clt.kafka.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KafkaProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProviderApplication.class,args);
    }
}
