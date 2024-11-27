package com.scrs.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class PhotoServiceImpl implements PhotoService {


	@Autowired
	private Cloudinary cloudinary;
	
	@Override
	public String savePic(MultipartFile profilePicture) throws IOException {
		String url = null;
		System.out.println("Is null at photo service? "+ profilePicture.isEmpty());
		if (!profilePicture.isEmpty()) {
			if (profilePicture.getSize() > MAX_SIZE) {
				throw new IllegalArgumentException("Profile picture is too large");
			}

			Map<?, ?> uploadResult = cloudinary.uploader().upload(profilePicture.getBytes(), ObjectUtils.emptyMap());
			url = (String) uploadResult.get("url");
			System.out.println(url);
			return url;
		}
		System.out.println("NULL IMAGE");
		return url;
	}

}
