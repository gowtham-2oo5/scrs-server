package com.scrs.service;

import java.io.IOException;
import java.util.List;

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

	@Autowired
	private CsvService csvService;

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
		String[] headers = { "Name", "SN" };
		List<DepartmentModel> depts = csvService.parseCsv(file, headers, record -> {
			DepartmentModel dept = new DepartmentModel();
			dept.setDeptName(record.get("Name"));
			dept.setSn(record.get("SN"));
			return dept;
		});

		saveDepts(depts);
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
