package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sales", schema = "demo_db")
@Getter
@Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sale")
    private Integer sale;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "product")
    private String product;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "store")
    private String store;

    @Column(name = "address")
    private String address;

    @Column(name = "unit")
    private String unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
