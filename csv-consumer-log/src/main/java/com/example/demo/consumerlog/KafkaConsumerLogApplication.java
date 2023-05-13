package com.example.demo.consumerlog;

import com.example.demo.consumerlog.config.KafkaConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(KafkaConsumerConfig.class)
public class KafkaConsumerLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerLogApplication.class, args);
	}

}
