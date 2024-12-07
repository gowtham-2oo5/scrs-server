package com.scrs.service;

import com.scrs.dto.ScheduleSlotDTO;
import com.scrs.dto.ScheduleTemplateDTO;
import com.scrs.model.BatchModel;
import com.scrs.model.ClusterModel;
import com.scrs.model.ScheduleSlot;
import com.scrs.model.ScheduleTemplate;
import com.scrs.repository.ScheduleTemplateRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleTemplateServiceImpl implements ScheduleTemplateService {

    @Autowired
    private ScheduleTemplateRepo scheduleTemplateRepo;

    @Autowired
    private ScheduleSlotService scheduleSlotService;

    @Autowired
    private BatchService batchService;

    @Autowired
    @Lazy
    private ClusterService clusterService; // Assuming a service for fetching ClusterModel


    @Transactional
    @Override
    public String createScheduleTemplate(ScheduleTemplateDTO dto, List<ScheduleSlotDTO> slots) {
        if (dto == null || slots == null || slots.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: DTO or slots list is null/empty");
        }

        ScheduleTemplate template = new ScheduleTemplate();
        template.setTitle(dto.getTitle());

        ClusterModel cluster = clusterService.getClusterRefById(UUID.fromString(dto.getCluster()));
//        if (cluster == null) {
//            throw new IllegalArgumentException("Invalid Cluster ID: " + dto.getClusterId());
//        }
        template.setCluster(cluster);

        BatchModel batch = batchService.getByName(dto.getBatch());
        template.setTemplate_batch(batch);

        scheduleTemplateRepo.save(template);

        System.out.println("TEMPLATE CREATED RAAAA");
        System.out.println(template.getId());

        List<ScheduleSlot> scheduleSlots = getScheduleSlots(slots);
        mapSlotsToTemplate(scheduleSlots, template);

        return "Template saved successfully";
    }

    @Override
    public ScheduleTemplateDTO getScheduleTemplate(UUID id) {
        ScheduleTemplate template = scheduleTemplateRepo.getReferenceById(id);
        ScheduleTemplateDTO dto = new ScheduleTemplateDTO();
        dto.setId(template.getId());
        dto.setTitle(template.getTitle());
        dto.setCluster((template.getCluster() == null) ? "None" : template.getCluster().getName());
        dto.setSlots(mapSlotsWithDTO(template.getSlots()));
        return dto;
    }

    private List<ScheduleSlotDTO> mapSlotsWithDTO(List<ScheduleSlot> slots) {

        List<ScheduleSlotDTO> res = new ArrayList<>();
        for (ScheduleSlot slot : slots) {
            ScheduleSlotDTO slotDTO = new ScheduleSlotDTO();
            slotDTO.setTimeSlot(slot.getTimeSlot().name());
            slotDTO.setDay(slot.getDay().name());
            slotDTO.setCourseCategory(slot.getCourseCategory().getTitle());
            res.add(slotDTO);
        }
        return res;

    }

    @Override
    public List<ScheduleTemplateDTO> getAllScheduleTemplates() {
        List<ScheduleTemplate> temps = scheduleTemplateRepo.findAll();
        List<ScheduleTemplateDTO> res = new ArrayList<>();

        for (ScheduleTemplate temp : temps) {
            ScheduleTemplateDTO dto = new ScheduleTemplateDTO();
            dto.setId(temp.getId());
            dto.setTitle(temp.getTitle());
            dto.setCluster((temp.getCluster() == null) ? "None" : temp.getCluster().getName());
            dto.setSlots(mapSlotsWithDTO(temp.getSlots()));
//            dto.setBatch(temp.get);
            res.add(dto);
        }

        return res;
    }

    @Override
    public ScheduleTemplate getScheduleTemplateById(UUID templateId) {
        return scheduleTemplateRepo.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Cluster not found with ID: " + templateId));
    }

    private void mapSlotsToTemplate(List<ScheduleSlot> slots, ScheduleTemplate template) {
        System.out.println("In map with template: ");
        System.out.println(template.toString());
        for (ScheduleSlot slot : slots) {
            System.out.println(slot.toString());
            slot.setTemplate(template); // Map each slot to the saved template
            scheduleSlotService.saveSlot(slot); // Save the slot
        }
    }

    private List<ScheduleSlot> getScheduleSlots(List<ScheduleSlotDTO> slots) {
        List<ScheduleSlot> slotList = new ArrayList<>();
        for (ScheduleSlotDTO slotDTO : slots) {
            ScheduleSlot newSlot = scheduleSlotService.createNewSlot(slotDTO);
            if (newSlot != null) {
                slotList.add(newSlot);
            }
        }
        return slotList;
    }
}
