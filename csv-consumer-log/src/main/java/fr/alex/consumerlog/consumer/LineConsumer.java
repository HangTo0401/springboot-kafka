package fr.alex.consumerlog.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.alex.consumerlog.config.KafkaConsumerConfig;
import fr.alex.consumerlog.io.LineMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LineConsumer {

    public static final String TOPIC = "test";

    private Logger logger = LoggerFactory.getLogger(LineConsumer.class);

    private final ObjectMapper objectMapper;

    public LineConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = TOPIC, groupId = KafkaConsumerConfig.CONSUMER_GROUP_ID)
    public void listen(String message) {
        try {
            LineMessage lineMessage = objectMapper.readValue(message, LineMessage.class);
            logger.info(lineMessage.toString());
        } catch (IOException e) {
            logger.error("Error while deserializing message: " + message, e);
        }
    }

}
