package com.scrs.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
@DiscriminatorValue("admin")
public class AdminModel extends UserModel {

	private boolean isSuperAdmin;

	public AdminModel() {
		super();
	}

	public AdminModel(String name, String username, String email, String password, String contact,
			byte[] profilePicture, boolean isSuperAdmin) {
		super(name, username, email, password, contact,UserRole.ADMIN, profilePicture); // Call to UserModel constructor
		this.isSuperAdmin = isSuperAdmin;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

}
