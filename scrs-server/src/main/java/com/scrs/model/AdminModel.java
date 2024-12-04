package com.scrs.model;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scrs_admins")
@DiscriminatorValue("ROLE_ADMIN")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdminModel extends UserModel {

	private boolean isSuperAdmin;

	public AdminModel(String name, String username, String email, String password, String contact,
					  String profilePicture, boolean isSuperAdmin, Date dob) {
		super(name, username, email, password, contact, UserRole.ADMIN, profilePicture, dob); // Call to UserModel constructor
		this.isSuperAdmin = isSuperAdmin;
	}
}
