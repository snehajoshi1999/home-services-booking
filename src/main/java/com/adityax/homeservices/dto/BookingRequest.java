package com.adityax.homeservices.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record BookingRequest(

        @NotNull(message = "Service id is required")
        Long serviceId,

        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotNull(message = "Phone is required")
        @Pattern(regexp = "\\d{10}", message = "Phone must be exactly 10 digits")
        String phone,

        @NotBlank(message = "Address cannot be empty")
        String address,

        @NotNull(message = "Date is required")
        @FutureOrPresent(message = "Date cannot be in the past")
        LocalDate date,

        @NotBlank(message = "Time slot is required")
        String timeSlot) {
}
