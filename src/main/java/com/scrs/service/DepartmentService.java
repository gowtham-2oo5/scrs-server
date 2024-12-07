package com.scrs.service;

import java.io.IOException;
import java.util.List;

import com.scrs.dto.DeptRegsDTO;
import com.scrs.model.DepartmentModel;

import org.springframework.web.multipart.MultipartFile;

public interface DepartmentService {

	public List<DepartmentModel> getAll();

	public void bulkInsertDepts(MultipartFile file) throws IOException;

	public void insertDept(DeptRegsDTO dept);

	DepartmentModel getDept(String sn);

	public void setHOD(String sn, String hodId);
	
	public void deleteDept(String id);

}
