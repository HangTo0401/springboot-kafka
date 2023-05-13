package com.example.demo.consumer;

import com.example.demo.service.ConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.Message;
import com.example.demo.config.KafkaConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class Consumer {

    public static final String TOPIC = "demo_topic";

    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final ObjectMapper objectMapper;
    private final ConsumerService consumerService;

    public Consumer(ObjectMapper objectMapper, ConsumerService consumerService) {
        this.objectMapper = objectMapper;
        this.consumerService = consumerService;
    }

    @KafkaListener(topics = TOPIC, groupId = KafkaConsumerConfig.CONSUMER_GROUP_ID)
    public void listen(String message) {
        try {
            Message returnedMessage = objectMapper.readValue(message, Message.class);
            logger.info(returnedMessage.toString());
            consumerService.save(returnedMessage);
        } catch (IOException e) {
            logger.error("Error while deserializing message: " + message, e);
        }
    }

}
