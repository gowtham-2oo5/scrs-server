package com.scrs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.SpcRegsDTO;
import com.scrs.model.SpecializationModel;

public interface SpecializationService {
	
	
	// Operations TO BE DONE:
	/*
	 * 1. Retrieve All
	 * 2. Create one 
	 * 3. Bulk create
	 * 4. Edit Specialization Department and SN
	 * 5. Delete single specialization
	 * 
	 * */
	
	
	public List<SpecializationModel> getAll();

	public void bulkInsertSpecs(MultipartFile file) throws IOException;

	public void insertSpec(SpcRegsDTO spec);
	
	public SpecializationModel getSpec(String sn);
	
	public String updateSpec(String sn, String deptSn);
	
	public String deleteSpec(String id);

}
