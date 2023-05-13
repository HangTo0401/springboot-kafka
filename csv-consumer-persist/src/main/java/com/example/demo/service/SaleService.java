package com.example.demo.service;

import com.example.demo.dto.SaleDTO;
import com.example.demo.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    private static final ModelMapper modelMapper = new ModelMapper();

    public Optional<List<SaleDTO>> findAllSalesAggregatedByProduct() {
        List<SaleDTO> sales = saleRepository.findAllSalesAggregatedByProduct().stream()
                .map(item -> modelMapper.map(item, SaleDTO.class))
                .collect(Collectors.toList());

        return Optional.ofNullable(sales);
    }
}
