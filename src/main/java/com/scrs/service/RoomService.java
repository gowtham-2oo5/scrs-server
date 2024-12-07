package com.scrs.service;

import com.scrs.dto.RoomCreationDTO;
import com.scrs.model.Room;
import com.scrs.model.ScheduleSlot;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface RoomService {

    public List<Room> getAllRooms();
    public Room getRoomById(UUID id);
    public Room createRoom(RoomCreationDTO room);
    public String bulkUploadRooms(MultipartFile file) throws IOException;
    public Room updateRoom(UUID id,RoomCreationDTO room) throws Exception;
    public void deleteRoom(UUID id);
    public Boolean isFree(ScheduleSlot slot, UUID id);

}
