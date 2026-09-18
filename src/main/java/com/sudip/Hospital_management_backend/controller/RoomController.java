package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.entity.Room;
import com.sudip.Hospital_management_backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomRepository rooms;

    // GET ALL ROOMS
    @GetMapping
    public List<Room> getAllRooms() {
        return rooms.findAll();
    }

    // GET ROOM BY ID
    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return rooms.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Room not found with id: " + id));
    }

    // CREATE ROOM
    @PostMapping
    public Room createRoom(@RequestBody Room room) {

        if (room.getRoomNumber() == null ||
                room.getRoomNumber().isBlank()) {

            throw new RuntimeException("Room number is required");
        }

        if (rooms.findByRoomNumber(room.getRoomNumber()).isPresent()) {
            throw new RuntimeException(
                    "Room number already exists: " + room.getRoomNumber()
            );
        }

        return rooms.save(room);
    }

    // UPDATE ROOM
    @PutMapping("/{id}")
    public Room updateRoom(
            @PathVariable Long id,
            @RequestBody Room data
    ) {

        Room room = rooms.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Room not found with id: " + id));

        if (data.getRoomNumber() != null &&
                !data.getRoomNumber().isBlank()) {

            room.setRoomNumber(data.getRoomNumber());
        }

        if (data.getType() != null) {
            room.setType(data.getType());
        }

        return rooms.save(room);
    }

    // DELETE ROOM
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {

        if (!rooms.existsById(id)) {
            throw new RuntimeException(
                    "Room not found with id: " + id);
        }

        rooms.deleteById(id);
    }
}