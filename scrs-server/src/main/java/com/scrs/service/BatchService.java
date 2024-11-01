package com.scrs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.BatchRegsDTO;
import com.scrs.model.BatchModel;

public interface BatchService {

	public void insertOne(BatchRegsDTO bDTO);

	public void bulkUpload(MultipartFile file) throws IOException;

	public List<BatchModel> getAll();

	public void delAll();

	public void updateSems(String name);

}
