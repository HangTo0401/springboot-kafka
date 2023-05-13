package com.example.demo.service;

import com.example.demo.domain.Sale;
import com.example.demo.entity.Message;
import com.example.demo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

public class ConsumerService {

    Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final SaleRepository salesRepository;

    public ConsumerService(SaleRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Transactional
    public void save(Message message) {
        // Mapping sales
        Sale sales = mapToSales(message);
        salesRepository.saveAndFlush(sales);

        logger.info("Record with id: {} saved", message.getId());
    }

    private Sale mapToSales(Message message) {
        Sale sales = salesRepository.findById(message.getId()).orElse(new Sale());
        sales.setId(message.getId());
        sales.setProduct(message.getProduct());
        sales.setSale(message.getSale());
        sales.setOrderDate(Date.from(message.getOrderDate().toInstant()));
        sales.setPrice(message.getPrice());
        sales.setUnit(message.getUnit());
        sales.setStore(message.getStore());
        sales.setAddress(message.getAddress());

        return sales;
    }
}
