package com.scrs.service;

import com.scrs.dto.SectionDTO;

import java.util.List;
import java.util.UUID;

public interface SectionService {

    List<SectionDTO> getAll();

    List<SectionDTO> gettAllByCluster(UUID clusterId);

    SectionDTO getById(int id);

    void createSection(SectionDTO section);

    void updateSection(UUID id, SectionDTO section);

    void deleteSection(UUID id);


}
