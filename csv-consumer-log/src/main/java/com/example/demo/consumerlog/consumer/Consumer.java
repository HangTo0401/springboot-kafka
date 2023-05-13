package com.example.demo.consumerlog.consumer;

import com.example.demo.consumerlog.config.KafkaConsumerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.consumerlog.io.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    public static final String TOPIC = "test";

    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final ObjectMapper objectMapper;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = TOPIC, groupId = KafkaConsumerConfig.CONSUMER_GROUP_ID)
    public void listen(String message) {
        try {
            Message lineMessage = objectMapper.readValue(message, Message.class);
            logger.info(lineMessage.toString());
        } catch (IOException e) {
            logger.error("Error while deserializing message: " + message, e);
        }
    }

}
