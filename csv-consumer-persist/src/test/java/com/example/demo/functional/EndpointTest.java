package com.example.demo.functional;

import com.example.demo.KafkaConsumerApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = KafkaConsumerApplication.class)
public class EndpointTest {

    private static final String SALES_API = "/api/sales";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void verifyTestRestTemplateNotNull() {
        assertNotNull(restTemplate);
    }

    @Test
    public void givenCorrectUrl_thenMakeCallApiSuccessfully() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity(SALES_API, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
