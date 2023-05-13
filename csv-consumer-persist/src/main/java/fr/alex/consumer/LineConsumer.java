package fr.alex.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.alex.io.LineMessage;
import fr.alex.service.LineService;
import fr.alex.config.KafkaConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

public class LineConsumer {

    public static final String TOPIC = "test";

    private Logger logger = LoggerFactory.getLogger(LineConsumer.class);

    private final ObjectMapper objectMapper;
    private final LineService lineService;

    public LineConsumer(ObjectMapper objectMapper, LineService lineService) {
        this.objectMapper = objectMapper;
        this.lineService = lineService;
    }

    @KafkaListener(topics = TOPIC, groupId = KafkaConsumerConfig.CONSUMER_GROUP_ID)
    public void listen(String message) {
        try {
            LineMessage lineMessage = objectMapper.readValue(message, LineMessage.class);
            logger.info(lineMessage.toString());
            lineService.saveLine(lineMessage);
        } catch (IOException e) {
            logger.error("Error while deserializing message: " + message, e);
        }
    }

}
