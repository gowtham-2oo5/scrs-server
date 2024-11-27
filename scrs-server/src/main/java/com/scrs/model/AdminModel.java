package com.scrs.model;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "scrs_admins")
@DiscriminatorValue("ROLE_ADMIN")
public class AdminModel extends UserModel {

	private boolean isSuperAdmin;

	public AdminModel() {
		super();
	}

	public AdminModel(String name, String username, String email, String password, String contact,
			String profilePicture, boolean isSuperAdmin, Date dob) {
		super(name, username, email, password, contact, UserRole.ADMIN, profilePicture, dob); // Call to UserModel
																							// constructor
		this.isSuperAdmin = isSuperAdmin;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	@Override
	public String toString() {
		return "AdminModel:" + super.toString() + " [isSuperAdmin=" + isSuperAdmin + "]";
	}

}
