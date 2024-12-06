package com.scrs.repository;

import com.scrs.model.ScheduleSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ScheduleSlotRepo extends JpaRepository<ScheduleSlot, UUID> {
}
