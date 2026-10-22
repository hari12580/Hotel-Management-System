	package com.hotel.service;
	
	import java.util.List;
	
	import org.springframework.stereotype.Service;
	
	import com.hotel.config.ResourceNotFoundException;
	import com.hotel.entity.Booking;
	import com.hotel.entity.Customer;
	import com.hotel.entity.Room;
	import com.hotel.repository.BookingRepository;
	import com.hotel.repository.CustomerRepository;
	import com.hotel.repository.RoomRepository;
	
	@Service
	public class BookingService {
	
	    private final BookingRepository bookingRepository;
	    private final CustomerRepository customerRepository;
	    private final RoomRepository roomRepository;
	
	    public BookingService(
	            BookingRepository bookingRepository,
	            CustomerRepository customerRepository,
	            RoomRepository roomRepository) {
	
	        this.bookingRepository = bookingRepository;
	        this.customerRepository = customerRepository;
	        this.roomRepository = roomRepository;
	    }
	
	    // Check-in customer
	    public Booking checkIn(
	            Long customerId,
	            Long roomId,
	            Booking booking) {
	
	        Customer customer = customerRepository
	                .findById(customerId)
	                .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                        "Customer not found"
	                    )
	                );
	
	        Room room = roomRepository
	                .findById(roomId)
	                .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                        "Room not found"
	                    )
	                );
	
	        if (!"AVAILABLE".equalsIgnoreCase(room.getStatus())) {
	            throw new RuntimeException(
	                "Room is not available"
	            );
	        }
	
	        booking.setCustomer(customer);
	        booking.setRoom(room);
	        booking.setStatus("CHECKED_IN");
	
	        Booking savedBooking =
	                bookingRepository.save(booking);
	
	        room.setStatus("BOOKED");
	        roomRepository.save(room);
	
	        return savedBooking;
	    }
	
	    // Get all bookings
	    public List<Booking> getAllBookings() {
	        return bookingRepository.findAll();
	    }
	
	    // Get booking by ID
	    public Booking getBookingById(Long id) {
	
	        return bookingRepository
	                .findById(id)
	                .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                        "Booking not found"
	                    )
	                );
	    }
	
	    // Get bookings by customer
	    public List<Booking> getBookingsByCustomer(
	            Long customerId) {
	
	        return bookingRepository
	                .findByCustomerId(customerId);
	    }
	
	    // Get bookings by room
	    public List<Booking> getBookingsByRoom(
	            Long roomId) {
	
	        return bookingRepository
					.findByRoomId(roomId);
		}
	}