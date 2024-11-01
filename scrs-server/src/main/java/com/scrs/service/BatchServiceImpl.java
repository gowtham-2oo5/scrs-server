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

import com.scrs.dto.BatchRegsDTO;
import com.scrs.model.BatchModel;
import com.scrs.model.SemesterEnum;
import com.scrs.model.YearEnum;
import com.scrs.repository.BatchRepo;

@Service
public class BatchServiceImpl implements BatchService {

	@Autowired
	private BatchRepo bRepo;

	@Override
	public void insertOne(BatchRegsDTO bDTO) {
		BatchModel batch = new BatchModel();
		batch.setName(bDTO.getName());
		batch.setCurrentYear(getCurrentYear(bDTO.getCurrYr()));
		bRepo.save(batch);
	}

	private YearEnum getCurrentYear(String currYr) {
		switch (currYr) {
		case "1":
			return YearEnum.FIRST;
		case "2":
			return YearEnum.SECOND;
		case "3":
			return YearEnum.THIRD;
		case "4":
			return YearEnum.FOURTH;
		}
		return null;
	}

	@Override
	public void bulkUpload(MultipartFile file) throws IOException {

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			CSVParser csvParser = new CSVParser(reader,
					CSVFormat.EXCEL.builder().setHeader("Name", "Current Year", "Current Semester", "eligible")
							.setSkipHeaderRecord(true).build());
			List<BatchModel> batches = new ArrayList<>();

			for (CSVRecord record : csvParser) {
				BatchModel batch = new BatchModel();

				batch.setName(record.get("Name"));
				batch.setCurrentYear(getCurrentYear(record.get("Current Year")));
				batch.setCurrentSem(getSemester(record.get("Current Semester")));
				batch.setEligibleForNextRegs(check(record.get("eligible")));
				batches.add(batch);
			}
			saveBs(batches);
			csvParser.close();
		}

	}

	private boolean check(String string) {
		return string.equalsIgnoreCase("Y");
	}

	private SemesterEnum getSemester(String string) {
		return SemesterEnum.valueOf(string.toUpperCase());
	}

	private void saveBs(List<BatchModel> batches) {
		for (BatchModel batch : batches) {
			bRepo.save(batch);
			System.out.println("Saved batch: " + batch.getName() + " " + batch.getId());
		}

	}

	@Override
	public List<BatchModel> getAll() {
		return bRepo.findAll();
	}

	@Override
	public void delAll() {
		for (BatchModel b : getAll()) {
			bRepo.delete(b);
		}
	}

	@Override
	public void updateSems(String name) {
		// Retrieve the batch by name
		BatchModel batch = bRepo.getBatchFromName(name);

		// Check the current year and perform updates based on the semester rules
		switch (batch.getCurrentYear()) {
		case FIRST: {
			// Transition for FIRST year
			switch (batch.getCurrentSem()) {
			case ODD:
				batch.setCurrentSem(SemesterEnum.EVEN);
				break;
			case EVEN:
				batch.setCurrentSem(SemesterEnum.SUMMER);
				break;
			case SUMMER:
				batch.setCurrentSem(SemesterEnum.ODD);
				batch.setCurrentYear(YearEnum.SECOND); // Move to SECOND year
				break;
			}
			batch.setEligibleForNextRegs(true); // Eligible for next registration
			break;
		}

		case SECOND: {
			// Transition for SECOND year
			switch (batch.getCurrentSem()) {
			case ODD:
				batch.setCurrentSem(SemesterEnum.EVEN);
				break;
			case EVEN:
				batch.setCurrentSem(SemesterEnum.SUMMER);
				break;
			case SUMMER:
				batch.setCurrentSem(SemesterEnum.ODD);
				batch.setCurrentYear(YearEnum.THIRD); // Move to THIRD year
				break;
			}
			batch.setEligibleForNextRegs(true); // Eligible for next registration
			break;
		}

		case THIRD: {
			// Transition for THIRD year
			switch (batch.getCurrentSem()) {
			case ODD:
				batch.setCurrentSem(SemesterEnum.EVEN);
				break;
			case EVEN:
				batch.setCurrentSem(SemesterEnum.SUMMER);
				break;
			case SUMMER:
				batch.setCurrentSem(SemesterEnum.ODD);
				batch.setCurrentYear(YearEnum.FOURTH); // Move to FOURTH year
				break;
			}
			batch.setEligibleForNextRegs(true); // Eligible for next registration
			break;
		}

		case FOURTH: {
			// Transition for FOURTH year
			switch (batch.getCurrentSem()) {
			case ODD:
				batch.setCurrentSem(SemesterEnum.EVEN);
				batch.setEligibleForNextRegs(true); // Eligible for EVEN semester
				break;
			case EVEN:
				batch.setCurrentSem(SemesterEnum.SUMMER);
				batch.setEligibleForNextRegs(false); // Ineligible in SUMMER semester
				break;
			case SUMMER:
				batch.setEligibleForNextRegs(false); // Ineligible if already in SUMMER
				break;
			}
			break;
		}
		}

		// Save the updated batch back to the repository if needed
		bRepo.save(batch);
	}

}
