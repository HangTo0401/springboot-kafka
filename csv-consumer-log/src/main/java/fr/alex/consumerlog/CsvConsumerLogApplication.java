package fr.alex.consumerlog;

import fr.alex.consumerlog.config.KafkaConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(KafkaConsumerConfig.class)
public class CsvConsumerLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvConsumerLogApplication.class, args);
	}

}
