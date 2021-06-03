package com.k.assignment.controller;

import com.k.assignment.dto.PriceRequest;
import com.k.assignment.dto.PriceResponse;
import com.k.assignment.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("price")
public class PriceController {

    private final PriceService priceService;

    @PostMapping
    private ResponseEntity<PriceResponse> calculateOptimal(@Valid @RequestBody PriceRequest priceRequest) {
        return ResponseEntity.ok(priceService.calculateOptimal(priceRequest));
    }

}
