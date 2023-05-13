package com.example.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Value("${topic.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(ProducerService.class);

    public ProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Message message) {
        try {
            kafkaTemplate.send(topicName, objectMapper.writeValueAsString(message));
            logger.info("Sent message: [id: {}, product: {}, orderDate: {}, sale: {}, price: {} {}, store: {}, address: {}]",
                    message.getId(),
                    message.getProduct(),
                    message.getOrderDate(),
                    message.getSale(),
                    message.getPrice(),
                    message.getUnit(),
                    message.getStore(),
                    message.getAddress());
        } catch (JsonProcessingException e) {
            logger.error("Error while serializing message" + message.toString(), e);
        }
    }
}
