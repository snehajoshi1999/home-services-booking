package com.adityax.homeservices.service;

import com.adityax.homeservices.dto.BookingRequest;
import com.adityax.homeservices.dto.BookingResponse;
import com.adityax.homeservices.entity.Booking;
import com.adityax.homeservices.entity.BookingStatus;
import com.adityax.homeservices.entity.HomeService;
import com.adityax.homeservices.exception.BadRequestException;
import com.adityax.homeservices.repository.BookingRepository;
import com.adityax.homeservices.repository.HomeServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private HomeServiceRepository homeServiceRepository;

    @InjectMocks
    private BookingService bookingService;

    private final HomeService plumbing =
            new HomeService("Plumbing", "Leak repair", new BigDecimal("199.00"), 4.3);

    private BookingRequest requestFor(Long serviceId) {
        return new BookingRequest(serviceId, "Ravi Kumar", "9876543210",
                "12 MG Road, Pune", LocalDate.now().plusDays(1), "10:00 AM - 12:00 PM");
    }

    @Test
    void createBooking_savesBookingWithPendingStatus() {
        when(homeServiceRepository.findById(1L)).thenReturn(Optional.of(plumbing));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponse response = bookingService.createBooking(requestFor(1L));

        assertEquals(BookingStatus.PENDING, response.status());
        assertEquals("Plumbing", response.serviceName());
        assertEquals("Ravi Kumar", response.name());
    }

    @Test
    void createBooking_throwsWhenServiceDoesNotExist() {
        when(homeServiceRepository.findById(99L)).thenReturn(Optional.empty());

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> bookingService.createBooking(requestFor(99L)));

        assertEquals("serviceId", ex.getField());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void cancelBooking_changesStatusToCancelled() {
        Booking booking = new Booking(plumbing, "Ravi Kumar", "9876543210",
                "12 MG Road, Pune", LocalDate.now().plusDays(1), "10:00 AM - 12:00 PM");
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingResponse response = bookingService.cancelBooking(1L);

        assertEquals(BookingStatus.CANCELLED, response.status());
    }
}
