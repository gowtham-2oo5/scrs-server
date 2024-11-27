package com.scrs.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
	long MAX_SIZE = 5 * 1024 * 1024;

	String savePic(MultipartFile profilePicture) throws IOException;

}
