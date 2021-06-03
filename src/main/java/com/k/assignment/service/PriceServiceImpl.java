package com.k.assignment.service;

import com.k.assignment.dto.PriceRequest;
import com.k.assignment.dto.PriceResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private static final String BOX = "box";
    private static final String PACK = "pack";
    private static final String BAR = "bar";

    /**
     * Calculates optimal combination of packages and single items to achieve minimal price and the price itself
     *
     * @param priceRequest - Object which contains data about single items and packages (size, price) and required items count
     */
    public PriceResponse calculateOptimal(PriceRequest priceRequest) {
        Integer countRemainder = priceRequest.getCount();
        List<Package> sortedList = getSortedList(priceRequest);
        BigDecimal totalPrice = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        int boxCount = 0;
        int packCount = 0;
        int barCount = 0;
        for (Package aPackage : sortedList) {
            while (countRemainder - aPackage.getSize() >= 0) {
                switch (aPackage.type) {
                    case BOX:
                        boxCount++;
                        break;
                    case PACK:
                        packCount++;
                        break;
                    case BAR:
                        barCount++;
                        break;
                    default:
                        break;
                }
                totalPrice = totalPrice.add(aPackage.getPrice());
                countRemainder -= aPackage.getSize();
            }
        }
        return PriceResponse.builder()
                .boxCount(boxCount)
                .packCount(packCount)
                .barCount(barCount)
                .totalPrice(totalPrice)
                .build();
    }

    private List<Package> getSortedList(PriceRequest priceRequest) {
        BigDecimal boxBarPrice = priceRequest.getBoxPrice().divide(BigDecimal.valueOf(priceRequest.getBoxSize()), 2, RoundingMode.HALF_EVEN);
        BigDecimal packBarPrice = priceRequest.getPackPrice().divide(BigDecimal.valueOf(priceRequest.getPackSize()), 2, RoundingMode.HALF_EVEN);
        List<Package> ordered = new ArrayList<>();
        ordered.add(Package.builder()
                .type(BOX)
                .size(priceRequest.getBoxSize())
                .price(priceRequest.getBoxPrice())
                .priceForSingle(boxBarPrice)
                .priority(1) // box should have greatest priority because it is the most convenient way to receive chocolate
                .build());
        ordered.add(Package.builder()
                .type(PACK)
                .size(priceRequest.getPackSize())
                .price(priceRequest.getPackPrice())
                .priceForSingle(packBarPrice)
                .priority(2) // pack should have greater priority than bar because buying packs is more convenient than buying single bars
                .build());
        ordered.add(Package.builder()
                .type(BAR)
                .size(1)
                .price(priceRequest.getBarPrice())
                .priceForSingle(priceRequest.getBarPrice())
                .priority(3)
                .build());
        ordered.sort(Comparator.comparing(Package::getPriceForSingle).thenComparing(Package::getPriority));
        return ordered;
    }

    @Data
    @Builder
    private static class Package {
        private Integer priority;
        private BigDecimal price;
        private BigDecimal priceForSingle;
        private Integer size;
        private String type;
    }

}
