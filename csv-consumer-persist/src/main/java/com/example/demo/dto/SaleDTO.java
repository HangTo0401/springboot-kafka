package com.example.demo.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SaleDTO {

    private Long id;
    private Integer sale;
    private Date orderDate;
    private String product;
    private BigDecimal price;
    private String unit;
    private String store;
    private String address;
}
