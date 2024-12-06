package com.scrs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "schedule_slots")
@Getter
@Setter
public class ScheduleSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TimeSlotEnum timeSlot;

    @Enumerated(EnumType.STRING)
    private DayEnum day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_category_id", nullable = false)
    private CourseCategory courseCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private ScheduleTemplate template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Override
    public String toString() {
        String templateTitle = (template != null) ? template.getTitle() : "No Template";
        String courseCategoryTitle = (courseCategory != null) ? courseCategory.getTitle() : "No Course Category";
        String dayName = (day != null) ? day.name() : "No Day";
        String timeSlotName = (timeSlot != null) ? timeSlot.name() : "No Time Slot";

        return "Template: " + templateTitle +
                "\nCourse Category: " + courseCategoryTitle +
                "\nDay-Time: " + dayName + " - " + timeSlotName;
    }


}
