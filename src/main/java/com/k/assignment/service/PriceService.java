package com.k.assignment.service;

import com.k.assignment.dto.PriceRequest;
import com.k.assignment.dto.PriceResponse;

public interface PriceService {

    PriceResponse calculateOptimal(PriceRequest priceRequest);

}
