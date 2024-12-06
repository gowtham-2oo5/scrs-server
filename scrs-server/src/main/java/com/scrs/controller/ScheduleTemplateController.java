package com.scrs.controller;


import com.scrs.dto.ScheduleTemplateDTO;
import com.scrs.service.ScheduleTemplateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/template")
@SecurityRequirement(name = "bearerAuth")
public class ScheduleTemplateController {

    @Autowired
    private ScheduleTemplateService scheduleTemplateService;

    @GetMapping
    public ResponseEntity<List<ScheduleTemplateDTO>> getAllTemplates() {
        try {
            List<ScheduleTemplateDTO> res = scheduleTemplateService.getAllScheduleTemplates();
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("get-template")
    public ResponseEntity<ScheduleTemplateDTO> getScheduleById(@RequestParam String id) {
        try {
            ScheduleTemplateDTO temp = scheduleTemplateService.getScheduleTemplate(UUID.fromString(id));
            return new ResponseEntity<>(temp, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTemplate(@RequestBody ScheduleTemplateDTO tempDTO) {
        try {
            scheduleTemplateService.createScheduleTemplate(tempDTO, tempDTO.getSlots());
            return ResponseEntity.ok().body("Template created");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
