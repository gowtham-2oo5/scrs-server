package com.scrs.service;

import com.scrs.dto.ScheduleSlotDTO;
import com.scrs.dto.ScheduleTemplateDTO;
import com.scrs.model.ScheduleTemplate;

import java.util.List;
import java.util.UUID;

public interface ScheduleTemplateService {

    public String createScheduleTemplate(ScheduleTemplateDTO dto, List<ScheduleSlotDTO> slots);

    public ScheduleTemplateDTO getScheduleTemplate(UUID id);

    public List<ScheduleTemplateDTO> getAllScheduleTemplates();

    ScheduleTemplate getScheduleTemplateById(UUID templateId);
}
