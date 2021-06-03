package com.k.assignment.controller;

import com.k.assignment.dto.PriceRequest;
import com.k.assignment.dto.PriceResponse;
import com.k.assignment.service.PriceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PriceService priceService;
    @Autowired
    private JacksonTester<PriceRequest> priceRequestJacksonTester;
    @Autowired
    private JacksonTester<PriceResponse> priceResponseJacksonTester;

    @Test
    @DisplayName("calculateOptimal calls correctly")
    void calculateOptimal_success() throws Exception {
        PriceRequest priceRequest = PriceRequest.builder()
                .count(150)
                .barPrice(BigDecimal.valueOf(2.3))
                .packPrice(BigDecimal.valueOf(25))
                .boxPrice(BigDecimal.valueOf(230))
                .packSize(12)
                .boxSize(120)
                .build();
        String reqJson = priceRequestJacksonTester.write(priceRequest).getJson();
        PriceResponse priceResponse = PriceResponse.builder()
                .boxCount(1)
                .packCount(2)
                .barCount(6)
                .totalPrice(BigDecimal.valueOf(293.80).setScale(2, RoundingMode.HALF_EVEN))
                .build();
        String respJson = priceResponseJacksonTester.write(priceResponse).getJson();

        when(priceService.calculateOptimal(priceRequest)).thenReturn(priceResponse);
        mockMvc.perform(
                post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(status().isOk())
                .andExpect(content().json(respJson));
    }

    @Test
    @DisplayName("calculateOptimal validation works correctly")
    void calculateOptimal_validationSuccess() throws Exception {
        PriceRequest priceRequest = PriceRequest.builder()
                .count(0)
                .barPrice(BigDecimal.valueOf(-1))
                .packPrice(BigDecimal.valueOf(0))
                .boxPrice(BigDecimal.valueOf(-5))
                .packSize(0)
                .boxSize(0)
                .build();
        String reqJson = priceRequestJacksonTester.write(priceRequest).getJson();

        mockMvc.perform(
                post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{" +
                        "\"packSize\":\"packSize must be greater than 0\"," +
                        "\"boxSize\":\"packSize must be greater than 0\"," +
                        "\"barPrice\":\"barPrice must be greater than 0\"," +
                        "\"boxPrice\":\"boxPrice must be greater than 0\"," +
                        "\"count\":\"count must be greater than 0\"," +
                        "\"packPrice\":\"packPrice must be greater than 0\"" +
                        "}"));
    }

}
