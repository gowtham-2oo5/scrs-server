package com.scrs.service;

import com.scrs.dto.ScheduleSlotDTO;
import com.scrs.model.CourseCategory;
import com.scrs.model.DayEnum;
import com.scrs.model.ScheduleSlot;
import com.scrs.model.TimeSlotEnum;
import com.scrs.repository.ScheduleSlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleSlotServiceImpl implements ScheduleSlotService {

    @Autowired
    private ScheduleSlotRepo scheduleSlotRepo;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Override
    public ScheduleSlot createNewSlot(ScheduleSlotDTO slotDTO) {
        if (slotDTO == null) {
            throw new IllegalArgumentException("Slot DTO cannot be null");
        }

        System.out.println("Creating new slot for template");
        ScheduleSlot newSlot = new ScheduleSlot();

        // Map day and time slot enums
        newSlot.setDay(getDay(slotDTO.getDay()));
        newSlot.setTimeSlot(getTimeSlot(slotDTO.getTimeSlot()));

        // Fetch the course category by title
        CourseCategory courseCategory = getCourseCategory(slotDTO.getCourseCategory());
        if (courseCategory == null) {
            throw new IllegalArgumentException("Invalid Course Category: " + slotDTO.getCourseCategory());
        }
        newSlot.setCourseCategory(courseCategory);

        return newSlot;
    }

    @Override
    public ScheduleSlot saveSlot(ScheduleSlot slot) {

        if (slot == null) {
            throw new IllegalArgumentException("Slot cannot be null");
        }

        System.out.println("Saving: " + slot.toString());
        return scheduleSlotRepo.save(slot);
    }

    private CourseCategory getCourseCategory(String courseCategory) {
        if (courseCategory == null || courseCategory.isEmpty()) {
            throw new IllegalArgumentException("Course Category cannot be null/empty");
        }
        return courseCategoryService.getById(courseCategory);
    }

    private TimeSlotEnum getTimeSlot(String timeSlot) {
        try {
            return TimeSlotEnum.valueOf(timeSlot);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid Time Slot: " + timeSlot, ex);
        }
    }

    private DayEnum getDay(String day) {
        try {
            return DayEnum.valueOf(day.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid Day: " + day, ex);
        }
    }
}
