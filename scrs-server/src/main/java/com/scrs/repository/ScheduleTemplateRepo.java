package com.scrs.repository;

import com.scrs.model.ScheduleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleTemplateRepo extends JpaRepository<ScheduleTemplate, UUID> {
}
