package com.example.demo.repository;

import com.example.demo.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT c FROM Sale as c GROUP BY c.product ORDER BY c.orderDate DESC")
    List<Sale> findAllSalesAggregatedByProduct();
}