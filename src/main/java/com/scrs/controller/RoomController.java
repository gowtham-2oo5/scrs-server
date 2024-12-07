package com.scrs.controller;


import com.scrs.dto.RoomCreationDTO;
import com.scrs.model.Room;
import com.scrs.service.RoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/rooms")
@SecurityRequirement(name = "bearerAuth")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody RoomCreationDTO room) {
        try {
            Room createdRoom = roomService.createRoom(room);
            return ResponseEntity.ok().body("Created room " + createdRoom.getRoomName());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/bulk-upload", consumes = "multipart/form-data")
    public ResponseEntity<?> bulkUploadRooms(@RequestPart MultipartFile file) {
        try {
            String res = roomService.bulkUploadRooms(file);
            if (res.startsWith("CSV"))
                return new ResponseEntity<>(res, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateRoom(@RequestParam String id, @RequestBody RoomCreationDTO room) {
        try {
            roomService.updateRoom(UUID.fromString(id), room);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRoom(@RequestParam UUID id) {
        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
