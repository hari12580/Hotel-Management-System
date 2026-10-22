package com.hotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotel.entity.Room;
import com.hotel.repository.RoomRepository;
import com.hotel.config.ResourceNotFoundException;
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room addRoom(Room room) {

        return roomRepository.save(room);
    }

    public List<Room> getAllRooms() {

        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {

        return roomRepository.findById(id)
        		.orElseThrow(() ->
        	    new ResourceNotFoundException("Room not found")
        	);    }

    public Room updateRoom(Long id, Room updatedRoom) {

        Room existingRoom = getRoomById(id);

        existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
        existingRoom.setRoomType(updatedRoom.getRoomType());
        existingRoom.setPrice(updatedRoom.getPrice());
        existingRoom.setStatus(updatedRoom.getStatus());

        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {

    	if (!roomRepository.existsById(id)) {
    	    throw new ResourceNotFoundException("Room not found");
    	}
        roomRepository.deleteById(id);
    }
}