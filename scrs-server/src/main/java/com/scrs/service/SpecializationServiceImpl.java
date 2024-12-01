package com.scrs.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scrs.dto.SpcRegsDTO;
import com.scrs.model.DepartmentModel;
import com.scrs.model.SpecializationModel;
import com.scrs.repository.SpecializationRepo;

@Service
public class SpecializationServiceImpl implements SpecializationService {

	@Autowired
	private SpecializationRepo specRepo;

	@Autowired
	private DepartmentService deptService;

	@Autowired
	private CsvService csvService;

	@Override
	public List<SpecializationModel> getAll() {
		return specRepo.findAll();
	}

	@Override
	public void bulkInsertSpecs(MultipartFile file) throws IOException {

		String[] headers = { "Name", "SN", "Dept" };
		List<SpecializationModel> specs = csvService.parseCsv(file, headers, record -> {
			SpecializationModel spec = new SpecializationModel();

			spec.setSpecName(record.get("Name"));
			spec.setSn(record.get("SN"));
			spec.setDept(getDept(record.get("Dept")));
			return spec;
		});

		saveSpecs(specs);

	}

	private void saveSpecs(List<SpecializationModel> specs) {
		for (SpecializationModel spec : specs) {
			spec.setStudentCount((long)0);
			specRepo.save(spec);
		}
	}

	@Override
	public void insertSpec(SpcRegsDTO specDTO) {
		try {
			SpecializationModel spec = new SpecializationModel();
			spec.setDept(getDept(specDTO.getDept()));
			spec.setSpecName(specDTO.getName());
			spec.setSn(specDTO.getSn());

			specRepo.save(spec);

		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	private DepartmentModel getDept(String dept) {
		System.out.println("Fetching department: " + dept);
		try {
			DepartmentModel department = deptService.getDept(dept);
			System.out.println("Fetched department: " + department);
			return department;
		} catch (Exception e) {
			System.err.println("Error fetching department: " + dept + ", Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SpecializationModel getSpec(String sn) {
		System.out.println("Fetching specialization with SN: " + sn);
		try {
			SpecializationModel spec = specRepo.findBySN(sn);
			System.out.println("Fetched specialization: " + spec);
			return spec;
		} catch (Exception e) {
			System.err.println("Error fetching specialization with SN: " + sn + ", Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String updateSpec(String sn, String deptSn) {
		System.out.println("Updating specialization with SN: " + sn);
		try {
			SpecializationModel spec = getSpec(sn);
			if (spec == null) {
				System.err.println("Specialization not found for SN: " + sn);
				return "Specialization not found";
			}
			DepartmentModel dept = deptService.getDept(deptSn);
			spec.setDept(dept);
			specRepo.save(spec);
			System.out.println("Updated specialization: " + spec);
			return "Updated Specialization";
		} catch (Exception e) {
			System.err.println("Error updating specialization: " + e.getMessage());
			e.printStackTrace();
			return "Error updating specialization";
		}
	}

	@Override
	public String deleteSpec(String id) {
		System.out.println("Deleting specialization with ID: " + id);
		try {
			UUID specId = UUID.fromString(id);
			SpecializationModel spec = specRepo.findByID(specId);
			if (spec == null) {
				System.err.println("Specialization not found for ID: " + id);
				return "Specialization not found";
			}
			specRepo.delete(spec);
			System.out.println("Deleted specialization: " + spec);
			return "Deleted successfully";
		} catch (Exception e) {
			System.err.println("Error deleting specialization: " + e.getMessage());
			e.printStackTrace();
			return "Error deleting specialization";
		}
	}
}
