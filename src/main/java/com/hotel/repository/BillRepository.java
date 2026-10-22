package com.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Optional<Bill> findByBookingId(Long bookingId);
}