package com.hotel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.Bill;
import com.hotel.service.CheckoutService;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin(origins = "http://localhost:5173")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<Bill> checkout(
            @PathVariable Long bookingId) {

        Bill bill = checkoutService.checkout(bookingId);

        return ResponseEntity.ok(bill);
    }

    @GetMapping("/bill/{id}")
    public ResponseEntity<Bill> getBill(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                checkoutService.getBillById(id)
        );
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Bill> getBillByBooking(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                checkoutService.getBillByBookingId(bookingId)
        );
    }
}