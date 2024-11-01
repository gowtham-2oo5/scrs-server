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

import com.scrs.dto.SpcRegsDTO;
import com.scrs.model.DepartmentModel;
import com.scrs.model.SpecializationModel;
import com.scrs.repository.DepartmentRepo;
import com.scrs.repository.SpecializationRepo;

@Service
public class SpecializationServiceImpl implements SpecializationService {

	@Autowired
	private SpecializationRepo specRepo;

	@Autowired
	private DepartmentRepo deptRepo;

	@Override
	public List<SpecializationModel> getAll() {
		return specRepo.findAll();
	}

	@Override
	public void bulkInsertSpecs(MultipartFile file) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			CSVParser csvParser = new CSVParser(reader,
					CSVFormat.EXCEL.builder().setHeader("Name", "SN", "Dept").build());
			List<SpecializationModel> specs = new ArrayList<>();

			for (CSVRecord record : csvParser) {
				SpecializationModel spec = new SpecializationModel();

				spec.setSpecName(record.get("Name"));
				spec.setSn(record.get("SN"));
				spec.setDept(getDept(record.get("Dept")));

				System.out.println(spec.toString());
				specs.add(spec);
			}
			specs.remove(0);
			// Pass the entities list to the repository layer for saving
			saveSpecs(specs);
			csvParser.close();
		}

	}

	private void saveSpecs(List<SpecializationModel> specs) {
		for (SpecializationModel spec : specs) {
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
		return deptRepo.findBySN(dept);
	}

	@Override
	public SpecializationModel getSpec(String sn) {
		return specRepo.findBySN(sn);
	}

}
