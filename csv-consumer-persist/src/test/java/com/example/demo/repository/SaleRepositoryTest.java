package com.example.demo.repository;

import com.example.demo.domain.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SaleRepositoryTest {

    @Mock
    private SaleRepository saleRepository;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenFindById_thenReturnSale() {
        Sale saleToSave = new Sale();
        saleToSave.setId(1L);
        saleToSave.setSale(5);
        saleToSave.setProduct("Iphone 14");
        saleToSave.setPrice(new BigDecimal(134.00));
        saleToSave.setUnit("USD");
        saleToSave.setStore("Amazon");

        Sale sale = new Sale();
        sale.setId(1L);
        sale.setSale(5);
        sale.setProduct("Iphone 14");
        sale.setPrice(new BigDecimal(134.00));
        sale.setUnit("USD");
        sale.setStore("Amazon");
        Mockito.when(saleRepository.save(sale)).thenReturn(saleToSave);

        saleRepository.saveAndFlush(sale);

        Mockito.when(saleRepository.findById(1L)).thenReturn(Optional.of(saleToSave));

        // when
        Optional<Sale> found = saleRepository.findById(1L);

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getProduct()).isEqualTo("Iphone 14");
    }
}
