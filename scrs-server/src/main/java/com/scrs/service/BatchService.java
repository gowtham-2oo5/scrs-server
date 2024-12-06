package com.scrs.service;

import com.scrs.dto.BatchRegsDTO;
import com.scrs.model.BatchModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface BatchService {

    BatchModel getByName(String name);

    public void insertOne(BatchRegsDTO bDTO);

    public void bulkUpload(MultipartFile file) throws IOException;

    public List<BatchModel> getAll();

    public void delAll();

    public String deleteBatch(String id);

    public void updateSems(String name);

    BatchModel getById(UUID batchId);
}
