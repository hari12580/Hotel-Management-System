package com.hotel.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.hotel.entity.Bill;
import com.hotel.entity.Booking;
import com.hotel.entity.Room;
import com.hotel.repository.BillRepository;
import com.hotel.repository.BookingRepository;
import com.hotel.repository.RoomRepository;

@Service
public class CheckoutService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BillRepository billRepository;

    public CheckoutService(
            BookingRepository bookingRepository,
            RoomRepository roomRepository,
            BillRepository billRepository) {

        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.billRepository = billRepository;
    }

    public Bill checkout(Long bookingId) {

        // 1. Find booking
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found"));

        // 2. Check booking status
        if (!"CHECKED_IN".equalsIgnoreCase(booking.getStatus())) {

            throw new RuntimeException(
                    "Booking is not currently checked in");
        }

        // 3. Get room
        Room room = booking.getRoom();

        // 4. Calculate number of days
        long numberOfDays = ChronoUnit.DAYS.between(
                booking.getCheckInDate(),
                booking.getCheckOutDate()
        );

        // Minimum one day
        if (numberOfDays <= 0) {
            numberOfDays = 1;
        }

        // 5. Calculate room charge
        double roomCharge =
                numberOfDays * room.getPrice();

        // 6. Create bill
        Bill bill = new Bill();

        bill.setBillDate(LocalDate.now());
        bill.setNumberOfDays(numberOfDays);
        bill.setRoomCharge(roomCharge);
        bill.setTotalAmount(roomCharge);
        bill.setPaymentStatus("PAID");
        bill.setBooking(booking);

        // 7. Save bill
        Bill savedBill = billRepository.save(bill);

        // 8. Change booking status
        booking.setStatus("CHECKED_OUT");

        bookingRepository.save(booking);

        // 9. Make room available
        room.setStatus("AVAILABLE");

        roomRepository.save(room);

        return savedBill;
    }

    public Bill getBillById(Long id) {

        return billRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bill not found"));
    }

    public Bill getBillByBookingId(Long bookingId) {

        return billRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Bill not found for this booking"));
    }
}