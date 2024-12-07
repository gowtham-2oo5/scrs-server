package com.scrs.service;

import com.scrs.dto.BatchRegsDTO;
import com.scrs.model.BatchModel;
import com.scrs.model.SemesterEnum;
import com.scrs.model.YearEnum;
import com.scrs.repository.BatchRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepo bRepo;

    @Override
    public BatchModel getByName(String name) {
        return bRepo.getBatchFromName(name);
    }

    @Override
    public void insertOne(BatchRegsDTO bDTO) {
        System.out.println("Inserting a single batch...");
        try {
            BatchModel batch = new BatchModel();
            batch.setName(bDTO.getName());
            batch.setCurrentYear(getCurrentYear(bDTO.getCurrentYear()));
            batch.setCurrentSem(getSemester("Odd"));
            batch.setEligibleForNextRegs(true);
            bRepo.save(batch);
            System.out.println("Batch inserted successfully: " + batch.getName());
        } catch (Exception e) {
            System.err.println("Error inserting batch: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public YearEnum getCurrentYear(String currYr) {
        System.out.println("Resolving current year from value: " + currYr);
        switch (currYr) {
            case "1":
                return YearEnum.FIRST;
            case "2":
                return YearEnum.SECOND;
            case "3":
                return YearEnum.THIRD;
            case "4":
                return YearEnum.FOURTH;
            default:
                System.err.println("Invalid year provided: " + currYr);
                return null;
        }
    }

    @Override
    public void bulkUpload(MultipartFile file) throws IOException {
        System.out.println("Starting bulk upload of batches...");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.EXCEL.builder().setHeader("Name", "Current Year", "Current Semester", "eligible")
                            .setSkipHeaderRecord(true).build());
            List<BatchModel> batches = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                try {
                    BatchModel batch = new BatchModel();
                    batch.setName(record.get("Name"));
                    batch.setCurrentYear(getCurrentYear(record.get("Current Year")));
                    batch.setCurrentSem(getSemester(record.get("Current Semester")));
                    batch.setEligibleForNextRegs(check(record.get("eligible")));
                    System.out.println("Parsed batch: " + batch);
                    batches.add(batch);
                } catch (Exception e) {
                    System.err.println("Error parsing record: " + record + ", Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            saveBs(batches);
            csvParser.close();
        } catch (Exception e) {
            System.err.println("Error in bulk upload: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean check(String string) {
        System.out.println("Checking eligibility value: " + string);
        return string.equalsIgnoreCase("Y");
    }

    private SemesterEnum getSemester(String string) {
        System.out.println("Resolving semester from value: " + string);
        try {
            return SemesterEnum.valueOf(string.toUpperCase());
        } catch (Exception e) {
            System.err.println("Invalid semester value: " + string);
            return null;
        }
    }

    private void saveBs(List<BatchModel> batches) {
        System.out.println("Saving batches...");
        for (BatchModel batch : batches) {
            try {
                batch.setStudentCount((long) 0);
                bRepo.save(batch);
                System.out.println("Saved batch: " + batch.getName() + " " + batch.getId());
            } catch (Exception e) {
                System.err.println("Error saving batch: " + batch.getName() + ", Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<BatchModel> getAll() {
        System.out.println("Fetching all batches...");
        try {
            List<BatchModel> batches = bRepo.findAll();
            System.out.println("Fetched batches count: " + batches.size());
            return batches;
        } catch (Exception e) {
            System.err.println("Error fetching batches: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void delAll() {
        System.out.println("Deleting all batches...");
        try {
            for (BatchModel b : getAll()) {
                bRepo.delete(b);
                System.out.println("Deleted batch: " + b.getName());
            }
        } catch (Exception e) {
            System.err.println("Error deleting all batches: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateSems(String name) {
        System.out.println("Updating semesters for batch: " + name);
        try {
            BatchModel batch = bRepo.getBatchFromName(name);

            if (batch == null) {
                System.err.println("Batch not found: " + name);
                return;
            }

            System.out.println("Current state of batch: " + batch);

            switch (batch.getCurrentYear()) {
                case FIRST:
                    switch (batch.getCurrentSem()) {
                        case ODD:
                            batch.setCurrentSem(SemesterEnum.EVEN);
                            break;
                        case EVEN:
                            batch.setCurrentSem(SemesterEnum.SUMMER);
                            break;
                        case SUMMER:
                            batch.setCurrentSem(SemesterEnum.ODD);
                            batch.setCurrentYear(YearEnum.SECOND);
                            break;
                    }
                    break;

                case SECOND:
                    switch (batch.getCurrentSem()) {
                        case ODD:
                            batch.setCurrentSem(SemesterEnum.EVEN);
                            break;
                        case EVEN:
                            batch.setCurrentSem(SemesterEnum.SUMMER);
                            break;
                        case SUMMER:
                            batch.setCurrentSem(SemesterEnum.ODD);
                            batch.setCurrentYear(YearEnum.THIRD);
                            break;
                    }
                    break;

                case THIRD:
                    switch (batch.getCurrentSem()) {
                        case ODD:
                            batch.setCurrentSem(SemesterEnum.EVEN);
                            break;
                        case EVEN:
                            batch.setCurrentSem(SemesterEnum.SUMMER);
                            break;
                        case SUMMER:
                            batch.setCurrentSem(SemesterEnum.ODD);
                            batch.setCurrentYear(YearEnum.FOURTH);
                            break;
                    }
                    break;

                case FOURTH:
                    switch (batch.getCurrentSem()) {
                        case ODD:
                            batch.setCurrentSem(SemesterEnum.EVEN);
                            break;
                        case EVEN:
                            batch.setCurrentSem(SemesterEnum.SUMMER);
                            batch.setEligibleForNextRegs(false); // Graduation check
                            break;
                        case SUMMER:
                            batch.setEligibleForNextRegs(false);
                            break;
                    }
                    break;

                default:
                    System.err.println("Invalid current year: " + batch.getCurrentYear());
                    return;
            }

            batch.setEligibleForNextRegs(true);
            bRepo.save(batch);
            System.out.println("Updated batch: " + batch);
        } catch (Exception e) {
            System.err.println("Error updating semesters for batch: " + name + ", Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public BatchModel getById(UUID batchId) {
        return bRepo.getReferenceById(batchId);
    }

    @Override
    public String deleteBatch(String id) {
        System.out.println("Deleting batch with ID: " + id);
        try {
            UUID bId = UUID.fromString(id);
            BatchModel batch = bRepo.findBatchById(bId);

            if (batch == null) {
                System.err.println("Batch not found for ID: " + id);
                return "Batch not found";
            }

            bRepo.delete(batch);
            System.out.println("Deleted batch: " + batch.getName());
            return "Batch deleted successfully";
        } catch (Exception e) {
            System.err.println("Error deleting batch: " + e.getMessage());
            e.printStackTrace();
            return "Error deleting batch";
        }
    }
}
