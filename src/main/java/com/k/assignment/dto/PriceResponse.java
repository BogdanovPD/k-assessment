package com.k.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceResponse {

    private Integer boxCount;
    private Integer packCount;
    private Integer barCount;
    private BigDecimal totalPrice;

}
