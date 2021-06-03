package com.k.assignment.service;

import com.k.assignment.dto.PriceRequest;
import com.k.assignment.dto.PriceResponse;

public interface PriceService {

    /**
     * Calculates optimal combination of packages and single items to achieve minimal price and the price itself
     *
     * @param priceRequest - Object which contains data about single items and packages (size, price) and required items count
     */
    PriceResponse calculateOptimal(PriceRequest priceRequest);

}
