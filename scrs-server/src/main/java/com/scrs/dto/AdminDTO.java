package com.scrs.dto;

import java.util.Base64;

import com.scrs.model.UserModel;
import com.scrs.model.UserRole;

public class AdminDTO {

	private int id;
	private String name;
	private String profilePicture;
	private boolean isSuperAdmin;
	private UserRole role;

	public AdminDTO(UserModel user, boolean superAdmin) {
		this.id = user.getId();
		this.name = user.getName();
		this.profilePicture = Base64.getEncoder().encodeToString(user.getProfilePicture());
		this.role = user.getUserRole();
		this.isSuperAdmin = superAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Sending this Admin DATA: [id=" + id + ", name=" + name + ", profilePicture=" + profilePicture
				+ ", isSuperAdmin=" + isSuperAdmin + ", role=" + role + "]";
	}

}
