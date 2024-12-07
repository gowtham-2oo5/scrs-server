package com.scrs.service;

import com.scrs.dto.SectionDTO;
import com.scrs.repository.SectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepo sectionRepo;

    @Override
    public List<SectionDTO> getAll() {
        return List.of();
    }

    @Override
    public List<SectionDTO> gettAllByCluster(UUID clusterId) {
        return List.of();
    }

    @Override
    public SectionDTO getById(int id) {
        return null;
    }

    @Override
    public void createSection(SectionDTO section) {

    }

    @Override
    public void updateSection(UUID id, SectionDTO section) {

    }

    @Override
    public void deleteSection(UUID id) {

    }
}
