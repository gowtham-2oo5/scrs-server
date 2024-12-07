package com.scrs.service;

import com.scrs.dto.DeptRegsDTO;
import com.scrs.model.DepartmentModel;
import com.scrs.model.FacultyModel;
import com.scrs.repository.DepartmentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
            d.setHod(facSer.getFacById(dept.getHodId()));
            d.setSn(dept.getSn());

            System.out.println(d.toString());
            deptRepo.save(d);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public DepartmentModel getDept(String sn) {
        System.out.println("Fetching DEPT with SN: " + sn);
        DepartmentModel dept = deptRepo.findBySN(sn);
        System.out.println("Received dept: " + dept.getDeptName());
        return dept;
    }

    @Override
    public void bulkInsertDepts(MultipartFile file) throws IOException {
        String[] headers = {"Name", "SN"};
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
            dept.setStudentCount((long) 0);
            deptRepo.save(dept);
            System.out.println("Saved Dept with ID: " + dept.getId());
        }
    }

    @Override
    public void setHOD(String sn, String hodId) {
        System.out.println("Setting HOD with ID: " + hodId + " for department with SN: " + sn);

        // Validate inputs
        if (sn == null || sn.isEmpty()) {
            throw new IllegalArgumentException("Department SN cannot be null or empty.");
        }
        if (hodId == null || hodId.isEmpty()) {
            throw new IllegalArgumentException("HOD ID cannot be null or empty.");
        }

        // Retrieve department
        DepartmentModel department = deptRepo.findBySN(sn);
        if (department == null) {
            System.out.println("Department not found with SN: " + sn);
            throw new EntityNotFoundException("Department with SN " + sn + " not found.");
        }

        // Retrieve and set HOD
        FacultyModel hod = getHod(hodId);
        if (hod == null) {
            System.out.println("Faculty not found with ID: " + hodId);
            throw new EntityNotFoundException("Faculty with ID " + hodId + " not found.");
        }


        department.setHod(hod);
        System.out.println("GOing to save HOD");
        deptRepo.save(department);
        System.out.println("SAVED HOD");
        System.out.println("HOD updated for department SN: " + sn + ". New HOD: " + hod.getName());
    }

    private FacultyModel getHod(String id) {
        System.out.println("Fetching HOD details for ID: " + id);

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("HOD ID cannot be null or empty.");
        }

        FacultyModel faculty = facSer.getFacById(id);
        if (faculty == null) {
            System.out.println("No faculty found with ID: " + id);
            throw new EntityNotFoundException("Faculty with ID " + id + " not found.");
        }

        System.out.println("Successfully retrieved HOD details: " + faculty.getName());
        return faculty;
    }

    public void deleteDept(String id) {
        try {
            // Check if the ID is null or empty
            if (id == null || id.isEmpty()) {
                System.out.println("Invalid ID: ID cannot be null or empty.");
                return;
            }

            // Convert String ID to UUID
            UUID originalId;
            try {
                originalId = UUID.fromString(id);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid ID format: " + id);
                return;
            }

            // Check if department exists
            DepartmentModel dept = deptRepo.findDeptById(originalId);
            if (dept == null) {
                System.out.println("Department not found for ID: " + id);
                return;
            }

//            System.out.println("Stundents in this dept: " + dept.getStudents());

            // Delete department
            System.out.println("Deleting Dept with ID: " + id);
            deptRepo.delete(dept);
            System.out.println("Deleted Dept with ID: " + id);

        } catch (Exception e) {
            // Handle unexpected exceptions
            System.out.println("An error occurred while deleting the department: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
