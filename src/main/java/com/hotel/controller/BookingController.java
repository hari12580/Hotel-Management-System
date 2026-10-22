package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.Booking;
import com.hotel.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/check-in")
    public ResponseEntity<Booking> checkIn(
            @RequestParam Long customerId,
            @RequestParam Long roomId,
            @RequestBody Booking booking) {

        Booking savedBooking =
                bookingService.checkIn(
                        customerId,
                        roomId,
                        booking
                );

        return ResponseEntity.ok(savedBooking);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Booking>> getByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByCustomer(customerId)
        );
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Booking>> getByRoom(
            @PathVariable Long roomId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByRoom(roomId)
        );
    }
}