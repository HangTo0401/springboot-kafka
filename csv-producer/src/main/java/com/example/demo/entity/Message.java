package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
public class Message {

    private long id;
    private String product;
    private ZonedDateTime orderDate;
    private Integer sale;
    private BigDecimal price;
    private String unit;
    private String store;
    private String address;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", product='" + product + '\'' +
                ", sale='" + sale + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", price='" + price + '\'' +
                ", unit='" + unit + '\'' +
                ", store='" + store + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
