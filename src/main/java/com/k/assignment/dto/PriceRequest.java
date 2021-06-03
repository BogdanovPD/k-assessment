package com.k.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {

    @NotNull
    @Min(value = 1, message = "count must be greater than 0")
    private Integer count;
    @NotNull
    @DecimalMin(value = "0.01", message = "barPrice must be greater than 0")
    private BigDecimal barPrice;
    @NotNull
    @DecimalMin(value = "0.01", message = "packPrice must be greater than 0")
    private BigDecimal packPrice;
    @NotNull
    @DecimalMin(value = "0.01", message = "boxPrice must be greater than 0")
    private BigDecimal boxPrice;
    @NotNull
    @Min(value = 1, message = "packSize must be greater than 0")
    private Integer packSize;
    @NotNull
    @Min(value = 1, message = "packSize must be greater than 0")
    private Integer boxSize;

}
