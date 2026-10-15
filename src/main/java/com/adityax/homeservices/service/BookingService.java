package com.adityax.homeservices.service;

import com.adityax.homeservices.dto.BookingRequest;
import com.adityax.homeservices.dto.BookingResponse;
import com.adityax.homeservices.entity.Booking;
import com.adityax.homeservices.entity.BookingStatus;
import com.adityax.homeservices.entity.HomeService;
import com.adityax.homeservices.exception.BadRequestException;
import com.adityax.homeservices.exception.ResourceNotFoundException;
import com.adityax.homeservices.repository.BookingRepository;
import com.adityax.homeservices.repository.HomeServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HomeServiceRepository homeServiceRepository;

    public BookingService(BookingRepository bookingRepository, HomeServiceRepository homeServiceRepository) {
        this.bookingRepository = bookingRepository;
        this.homeServiceRepository = homeServiceRepository;
    }

    public BookingResponse createBooking(BookingRequest request) {
        HomeService service = homeServiceRepository.findById(request.serviceId())
                .orElseThrow(() -> new BadRequestException("serviceId",
                        "Service not found with id " + request.serviceId()));

        Booking booking = new Booking(
                service,
                request.name().trim(),
                request.phone(),
                request.address().trim(),
                request.date(),
                request.timeSlot().trim());

        return BookingResponse.from(bookingRepository.save(booking));
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(BookingResponse::from)
                .toList();
    }

    public BookingResponse cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("status", "Booking is already cancelled");
        }

        booking.cancel();
        return BookingResponse.from(bookingRepository.save(booking));
    }
}
