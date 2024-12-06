package com.scrs.repository;

import com.scrs.model.SectionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SectionRepo extends JpaRepository<SectionModel, UUID> {
}
