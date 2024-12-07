package com.scrs.service;

import com.scrs.dto.CourseCreationDTO;
import com.scrs.dto.CourseDTO;
import com.scrs.dto.SectionDTO;
import com.scrs.model.*;
import com.scrs.repository.CourseRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo cRepo;

    @Autowired
    private CsvService csvService;

    @Autowired
    private CourseCategoryService ccService;

    @Autowired
    private DepartmentService deptService;

    @Autowired
    private SpecializationService specService;

    @Autowired
    private FacultyService facService;

    @Autowired
    private BatchServiceImpl batchService;
    private CourseRepo courseRepo;

    @Override
    public CourseModel singleCreate(CourseCreationDTO dto) {
        try {
            CourseModel course = new CourseModel();

            course.setCourseCode(dto.getCourseCode());
            course.setCourseTitle(dto.getCourseTitle());
            course.setCourseDesc(dto.getCourseDesc());
            CourseCategory category = ccService.getCcByTitle(dto.getCourseCategory());
            course.setCategory(category);
            course.setIncharge(facService.getFacById(dto.getCourseIncharge()));
            course.setOfferingDept(deptService.getDept(dto.getOfferingDept()));
            Boolean openForAll = dto.getIsOpenForAll();
            if (!openForAll) {
                course.setTargetDepts(getDepartments(dto.getTargetDepts()));
                course.setTargetSpecializations(getSpecializations(dto.getTargetSpecs()));
            } else {
                course.setTargetDepts(null);
                course.setTargetSpecializations(null);
            }
            course.setOpenForAll(openForAll);
            course.setYear(batchService.getCurrentYear(dto.getForStudentsOfYear()));

            CourseModel preReq = cRepo.getCourseByCode(dto.getPreReqCourse());
            course.setPreReqCourse(preReq);

            course.setL(dto.getL());
            course.setP(dto.getP());
            course.setT(dto.getT());
            course.setS(dto.getS());

            // Save the course and update the category
            cRepo.save(course);
            ccService.updateCC(category, course);

            return course;
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    private List<SpecializationModel> getSpecializations(List<String> targetSpecs) {
        List<SpecializationModel> res = new ArrayList<>();
        for (String sn : targetSpecs) {
            res.add(specService.getSpec(sn));
        }
        return res;
    }

    private List<DepartmentModel> getDepartments(List<String> targetDepts) {
        List<DepartmentModel> res = new ArrayList<>();
        for (String sn : targetDepts) {
            res.add(deptService.getDept(sn));
        }
        return res;
    }

    @Override
    public String bulkUploadCourses(MultipartFile file) {
        String[] headers = {"courseCode", "courseTitle", "courseDesc", "courseCategory", "courseIncharge",
                "offeringDept", "targetDepts", "targetSpecs", "isOpenForAll", "forStudentsOfYear", "L", "T", "P", "S",
                "preReqCourse"};

        try {
            List<CourseModel> courses = csvService.parseCsv(file, headers, record -> {
                CourseModel course = new CourseModel();

                course.setCourseCode(record.get("courseCode"));
                course.setCourseTitle(record.get("courseTitle"));
                course.setCourseDesc(record.get("courseDesc"));

                CourseCategory category = ccService.getCcByTitle(record.get("courseCategory"));
                course.setCategory(category);

                course.setIncharge(facService.getFacById(record.get("courseIncharge")));
                course.setOfferingDept(deptService.getDept(record.get("offeringDept")));

                Boolean isOpenForAll = Boolean.parseBoolean(record.get("isOpenForAll"));
                course.setOpenForAll(isOpenForAll);

                if (!isOpenForAll) {
                    List<String> targetDepts = List.of(record.get("targetDepts").split(","));
                    List<String> targetSpecs = List.of(record.get("targetSpecs").split(","));
                    course.setTargetDepts(getDepartments(targetDepts));
                    course.setTargetSpecializations(getSpecializations(targetSpecs));
                } else {
                    course.setTargetDepts(null);
                    course.setTargetSpecializations(null);
                }

                course.setYear(batchService.getCurrentYear(record.get("forStudentsOfYear")));

                String preReqCourseCode = record.get("preReqCourse");
                course.setPreReqCourse(preReqCourseCode != null && !preReqCourseCode.isEmpty()
                        ? cRepo.getCourseByCode(preReqCourseCode)
                        : null);

                course.setL(Double.parseDouble(record.get("L")));
                course.setT(Double.parseDouble(record.get("T")));
                course.setP(Double.parseDouble(record.get("P")));
                course.setS(Double.parseDouble(record.get("S")));

                ccService.updateCC(category, course);

                return course;
            });

            cRepo.saveAll(courses);
            return "Courses uploaded successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading courses: " + e.getLocalizedMessage();
        }
    }

    @Override
    public List<CourseDTO> getAll() {
        List<CourseModel> courses = cRepo.findAll();
        List<CourseDTO> resCourses = new ArrayList<>();

        // Convert each CourseModel to CourseDTO
        courses.forEach(course -> {
            CourseDTO temp = mapToDTO(course);
            resCourses.add(temp);
        });

        return resCourses;
    }

    // Helper method to map CourseModel to CourseDTO
    private CourseDTO mapToDTO(CourseModel course) {
        CourseDTO temp = new CourseDTO();

        temp.setId(course.getId());
        temp.setCourseCode(course.getCourseCode());
        temp.setCourseTitle(course.getCourseTitle());
        temp.setCourseDesc(course.getCourseDesc());

        // Handling null values safely
        String inchargeName = (course.getIncharge() != null && course.getIncharge().getEmpId() != null)
                ? facService.getFacById(course.getIncharge().getEmpId()).getName()
                : "No Incharge Assigned";
        temp.setCourseIncharge(inchargeName);
        temp.setCourseInchargeId(course.getIncharge().getEmpId());

        String offeringDept = (course.getOfferingDept() != null)
                ? deptService.getDept(course.getOfferingDept().getSn()).getSn()
                : "No Department Assigned";
        temp.setOfferingDept(offeringDept);

        String whoCanRegister = course.isOpenForAll()
                ? "Any student irrespective of department or specialization"
                : "Students of department: " + course.getTargetDepts()
                .stream()
                .map(dept -> dept.getDeptName())
                .collect(Collectors.joining(", ")) + " only";
        temp.setWhoCanRegister(whoCanRegister);

        temp.setCredits(course.getCredits());
        temp.setL(course.getL());
        temp.setT(course.getT());
        temp.setP(course.getP());
        temp.setS(course.getS());

        return temp;
    }


    @Override
    public String updateLTPS(UUID id, Double L, Double T, Double P, Double S) {
        try {
            CourseModel course = cRepo.getCourseById(id);
            course.setL(L);
            course.setP(P);
            course.setT(T);
            course.setS(S);
            cRepo.save(course);
            return "Updated L-T-P-S successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating LTPS: " + e.getLocalizedMessage();
        }
    }

    @Override
    public String updateCC(UUID id, String empId) {
        try {
            CourseModel course = cRepo.getCourseById(id);
            course.setIncharge(facService.getFacById(empId));
            cRepo.save(course);
            return "Updated CC successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating CC: " + e.getLocalizedMessage();
        }
    }

    @Override
    public String updateCourseTitle(UUID id, String title) {
        try {
            CourseModel course = cRepo.getCourseById(id);
            course.setCourseTitle(title);
            cRepo.save(course);
            return "Updated Course Title successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating Course title: " + e.getLocalizedMessage();
        }
    }

    @Override
    public String deleteCourse(UUID id) {
        try {
            CourseModel course = cRepo.getCourseById(id);
            cRepo.delete(course);
            return "Deleted course successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error deleting course: " + e.getLocalizedMessage();
        }
    }

    @Override
    public List<DepartmentModel> getTargetDepts(UUID id) {
        return cRepo.getCourseById(id).getTargetDepts();
    }

    @Override
    public List<SectionDTO> getSectionOfCourse(UUID id) throws Exception {
        List<SectionDTO> res = null;
        CourseModel course = cRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find course with ID: " + id));
        res = mapSectionDTO(course.getSections());
        return res;
    }

    private List<SectionDTO> mapSectionDTO(List<SectionModel> sections) {
        List<SectionDTO> res = new ArrayList<>();
        sections.forEach(section -> {
            SectionDTO temp = new SectionDTO();
            temp.setCourseId(section.getCourse().getId());
            temp.setSectionId(section.getId());
            temp.setCourseName(section.getCourse().getCourseTitle());
            temp.setCourseCategoryId(section.getCourse().getCategory().getId());
            temp.setClusterName(section.getCluster().getName());
            temp.setClusterId(section.getCluster().getId());
            temp.setRoomId(section.getRoom().getId());
            temp.setRoomName(section.getRoom().getRoomName());
        });

        return res;
    }

    @Override
    public List<SpecializationModel> getTargetSpecs(UUID id) {
        return cRepo.getCourseById(id).getTargetSpecializations();
    }

}
