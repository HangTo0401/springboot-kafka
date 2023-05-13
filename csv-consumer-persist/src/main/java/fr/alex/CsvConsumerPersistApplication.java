package fr.alex;

import fr.alex.config.KafkaConsumerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(KafkaConsumerConfig.class)
public class CsvConsumerPersistApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvConsumerPersistApplication.class, args);
	}

}
