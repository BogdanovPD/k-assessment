package com.k.assignment.service;

import com.k.assignment.dto.PriceRequest;
import com.k.assignment.dto.PriceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class PriceServiceTest {

    @Test
    @DisplayName("Optimal combination calculates correctly")
    void calculateOptimal_success() {
        PriceService priceService = new PriceServiceImpl();
        PriceRequest priceRequest = PriceRequest.builder()
                .count(150)
                .barPrice(BigDecimal.valueOf(2.3))
                .packPrice(BigDecimal.valueOf(25))
                .boxPrice(BigDecimal.valueOf(230))
                .packSize(12)
                .boxSize(120)
                .build();
        PriceResponse priceResponse = priceService.calculateOptimal(priceRequest);
        assertThat(priceResponse, is(PriceResponse.builder()
                .boxCount(1)
                .packCount(2)
                .barCount(6)
                .totalPrice(BigDecimal.valueOf(293.80).setScale(2, RoundingMode.HALF_EVEN))
                .build()));
    }

    @Test
    @DisplayName("If single item for a pack and a box are of the same price - prefer box")
    void calculateOptimal_preferBoxToPack() {
        PriceService priceService = new PriceServiceImpl();
        PriceRequest priceRequest = PriceRequest.builder()
                .count(150)
                .barPrice(BigDecimal.valueOf(2.3))
                .packPrice(BigDecimal.valueOf(24))
                .boxPrice(BigDecimal.valueOf(240))
                .packSize(12)
                .boxSize(120)
                .build();
        PriceResponse priceResponse = priceService.calculateOptimal(priceRequest);
        assertThat(priceResponse, is(PriceResponse.builder()
                .boxCount(1)
                .packCount(2)
                .barCount(6)
                .totalPrice(BigDecimal.valueOf(301.80).setScale(2, RoundingMode.HALF_EVEN))
                .build()));
    }

    @Test
    @DisplayName("If single item for a pack and a bar are of the same price - prefer pack")
    void calculateOptimal_preferPackToBar() {
        PriceService priceService = new PriceServiceImpl();
        PriceRequest priceRequest = PriceRequest.builder()
                .count(150)
                .barPrice(BigDecimal.valueOf(2))
                .packPrice(BigDecimal.valueOf(12))
                .boxPrice(BigDecimal.valueOf(230))
                .packSize(6)
                .boxSize(120)
                .build();
        PriceResponse priceResponse = priceService.calculateOptimal(priceRequest);
        assertThat(priceResponse, is(PriceResponse.builder()
                .boxCount(1)
                .packCount(5)
                .barCount(0)
                .totalPrice(BigDecimal.valueOf(290.00).setScale(2, RoundingMode.HALF_EVEN))
                .build()));
    }

    @Test
    @DisplayName("If single item for a box and a bar are of the same price - prefer box")
    void calculateOptimal_preferBoxToBar() {
        PriceService priceService = new PriceServiceImpl();
        PriceRequest priceRequest = PriceRequest.builder()
                .count(150)
                .barPrice(BigDecimal.valueOf(2))
                .packPrice(BigDecimal.valueOf(25))
                .boxPrice(BigDecimal.valueOf(240))
                .packSize(12)
                .boxSize(120)
                .build();
        PriceResponse priceResponse = priceService.calculateOptimal(priceRequest);
        assertThat(priceResponse, is(PriceResponse.builder()
                .boxCount(1)
                .packCount(0)
                .barCount(30)
                .totalPrice(BigDecimal.valueOf(300.00).setScale(2, RoundingMode.HALF_EVEN))
                .build()));
    }

}
