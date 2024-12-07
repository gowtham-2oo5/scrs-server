package com.scrs.service;

import com.scrs.dto.FacultyDTO;
import com.scrs.dto.FacultyRegsDTO;
import com.scrs.model.FacultyModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FacultyService {

    final String profilePictureUrl = "http://res.cloudinary.com/scrs-user-profile-pictures/image/upload/v1732555250/ay8jqjqva1rlhsqpdhmb.png";


    public FacultyModel createFaculty(FacultyRegsDTO facDetails, MultipartFile profilePicture) throws IOException;

    public void delFac(String uname);

    public List<FacultyDTO> getAll();

    public UUID createSingleFaculty(FacultyRegsDTO facDTO);

    public void bulkInsertDepts(MultipartFile file) throws IOException;

    List<FacultyDTO> getFacultiesByDept(String sn);

    FacultyModel findByUsername(String uname);


    FacultyModel getFacById(String hodId);

    public FacultyDTO getFacultyByEmpId(String empId);

}