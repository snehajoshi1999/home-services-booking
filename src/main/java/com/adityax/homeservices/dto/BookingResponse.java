package com.adityax.homeservices.dto;

import com.adityax.homeservices.entity.Booking;
import com.adityax.homeservices.entity.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long serviceId,
        String serviceName,
        String name,
        String phone,
        String address,
        LocalDate date,
        String timeSlot,
        BookingStatus status,
        LocalDateTime createdAt) {

    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getService().getId(),
                booking.getService().getName(),
                booking.getCustomerName(),
                booking.getPhone(),
                booking.getAddress(),
                booking.getBookingDate(),
                booking.getTimeSlot(),
                booking.getStatus(),
                booking.getCreatedAt());
    }
}
