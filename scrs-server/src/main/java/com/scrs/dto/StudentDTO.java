package com.scrs.dto;

import java.util.UUID;

import com.scrs.model.UserModel;
import com.scrs.model.UserRole;

public class StudentDTO {

	private UUID id;
	private String name;
	private String profilePicture;
	private String regNum;
	private UserRole role;

	public StudentDTO(UserModel user, String regNum) {
		this.id = user.getId();
		this.name = user.getName();
		this.profilePicture = user.getProfilePicture();
		this.regNum = regNum;
		this.role = user.getUserRole();
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
