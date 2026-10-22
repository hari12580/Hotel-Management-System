package com.hotel.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hotel.entity.Room;
import com.hotel.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {

        Room savedRoom = roomService.addRoom(room);

        return ResponseEntity.ok(savedRoom);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {

        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {

        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long id,
            @RequestBody Room room) {

        return ResponseEntity.ok(
                roomService.updateRoom(id, room)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {

        roomService.deleteRoom(id);

        return ResponseEntity.ok("Room deleted successfully");
    }
}