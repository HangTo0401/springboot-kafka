package com.example.demo.controller;

import com.example.demo.dto.SaleDTO;
import com.example.demo.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping("/sales")
    public ResponseEntity<?> getSales() {
        Optional<List<SaleDTO>> optionalSaleDTOS = saleService.findAllSalesAggregatedByProduct();

        if (!optionalSaleDTOS.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalSaleDTOS.get());
    }
}
