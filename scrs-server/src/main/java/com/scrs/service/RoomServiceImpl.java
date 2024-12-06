package com.scrs.service;

import com.scrs.dto.RoomCreationDTO;
import com.scrs.model.Room;
import com.scrs.model.ScheduleSlot;
import com.scrs.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CsvService csvService;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(UUID id) {
        return roomRepository.findByRoomId(id);
    }

    @Override
    public Room createRoom(RoomCreationDTO dto) {
        Room room = new Room();
        room.setBlock(dto.getBlock());
        room.setBookedSlots(null);
        room.setSections(null);
        roomRepository.save(room);
        System.out.println("Saved room with name: " + room.getRoomName());
        return room;
    }

    @Override
    public String bulkUploadRooms(MultipartFile file) throws IOException {
        try {
            String headers[] = {"Block", "Floor", "roomNum", "type", "capacity"};
            List<Room> rooms = csvService.parseCsv(file, headers, record -> {
                Room room = new Room();
                room.setBlock(record.get("Block"));
                room.setFloor(Integer.parseInt(record.get("Floor")));
                room.setRoomNum(Integer.parseInt(record.get("roomNum")));
                room.setRoomType(record.get("type"));
                room.setCapacity(Integer.parseInt(record.get("capacity")));
                room.setSections(null);
                room.setBookedSlots(null);
                return room;
            });
            roomRepository.saveAll(rooms);
            return "CSV File processed successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing file, " + e.getMessage();
        }
    }

    @Override
    public Room updateRoom(UUID id, RoomCreationDTO dto) {
        Room room = new Room();
        room.setRoomType(dto.getRoomType());
        room.setCapacity(dto.getCapacity());
        room.setBlock(dto.getBlock());
        room.setFloor(dto.getFloor());
        room.setRoomNum(dto.getNumber());

        Room r = roomRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with name" + room.getRoomName() + "not found"));
        r.setBlock(room.getBlock());
        r.setSections(room.getSections());
        r.setFloor(room.getFloor());
        r.setBookedSlots(room.getBookedSlots());
        roomRepository.save(r);
        System.out.println("Updated room with name: " + room.getRoomName());
        return r;
    }

    @Override
    public void deleteRoom(UUID id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Boolean isFree(ScheduleSlot slot, UUID id) {
        return roomRepository.findByRoomId(id).getBookedSlots().contains(slot);
    }
}
