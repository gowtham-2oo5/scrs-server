package com.scrs.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.DeptRegsDTO;
import com.scrs.model.DepartmentModel;
import com.scrs.model.FacultyModel;
import com.scrs.repository.DepartmentRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo deptRepo;

	@Autowired
	private FacultyService facSer;

	@Override
	public List<DepartmentModel> getAll() {
		return deptRepo.findAll();
	}

	@Override
	public void insertDept(DeptRegsDTO dept) {
		try {
			DepartmentModel d = new DepartmentModel();

			d.setCourses(null);
			d.setDeptName(dept.getName());
			d.setHod(facSer.findByUsername(dept.getHodId()));
			d.setSn(dept.getSn());

			deptRepo.save(d);
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	@Override
	public DepartmentModel getDept(String sn) {
		return deptRepo.findBySN(sn);
	}

	@Override
	public void bulkInsertDepts(MultipartFile file) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.builder().setHeader("Name", "SN").build());
			List<DepartmentModel> depts = new ArrayList<>();

			for (CSVRecord record : csvParser) {
				/*
				 * MyEntity entity = new MyEntity(); entity.setField1(csvRecord.get("Field1"));
				 * entity.setField2(csvRecord.get("Field2")); // Set other fields as necessary
				 * entities.add(entity);
				 */
				DepartmentModel dept = new DepartmentModel();
				dept.setDeptName(record.get("Name"));
				dept.setSn(record.get("SN"));
				System.out.println(dept.toString());
				depts.add(dept);
			}
			depts.remove(0);
			// Pass the entities list to the repository layer for saving
			saveDepts(depts);
			csvParser.close();
		}
	}

	private void saveDepts(List<DepartmentModel> depts) {
		for (DepartmentModel dept : depts) {
		
			deptRepo.save(dept);
			System.out.println("Saved Dept with ID: " + dept.getId());
		}
	}

	@Override
	public void setHOD(String sn, String uname) {
		DepartmentModel dept = deptRepo.findBySN(sn);
		if (dept != null) {
			dept.setHod(getHod(uname));
			deptRepo.save(dept);
		} else {
			throw new EntityNotFoundException("Department with SN " + sn + " not found.");
		}
	}

	private FacultyModel getHod(String uname) {
		return facSer.findByUsername(uname);
	}

}
