package com.adityax.homeservices.dto;

import com.adityax.homeservices.entity.HomeService;

import java.math.BigDecimal;

public record ServiceResponse(
        Long id,
        String name,
        String description,
        BigDecimal startingPrice,
        Double rating) {

    public static ServiceResponse from(HomeService service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getStartingPrice(),
                service.getRating());
    }
}
