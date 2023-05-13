package com.example.demo.config;

import com.example.demo.repository.*;
import com.example.demo.service.ConsumerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ConsumerService consumerService(SaleRepository salesRepository) {
        return new ConsumerService(salesRepository);
    }

}
