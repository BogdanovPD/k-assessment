package com.k.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {

    private Integer count;
    private BigDecimal barPrice;
    private BigDecimal packPrice;
    private BigDecimal boxPrice;
    private Integer packSize;
    private Integer boxSize;

}
