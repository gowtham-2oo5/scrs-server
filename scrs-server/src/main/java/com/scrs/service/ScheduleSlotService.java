package com.scrs.service;

import com.scrs.dto.ScheduleSlotDTO;
import com.scrs.model.ScheduleSlot;

public interface ScheduleSlotService {

    public ScheduleSlot createNewSlot(ScheduleSlotDTO slot);

    ScheduleSlot saveSlot(ScheduleSlot slot);
}
