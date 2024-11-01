package com.scrs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.SpcRegsDTO;
import com.scrs.model.SpecializationModel;

public interface SpecializationService {
	public List<SpecializationModel> getAll();

	public void bulkInsertSpecs(MultipartFile file) throws IOException;

	public void insertSpec(SpcRegsDTO spec);

	SpecializationModel getSpec(String sn);

}
