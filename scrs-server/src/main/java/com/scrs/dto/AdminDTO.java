package com.scrs.dto;

import java.util.UUID;

import com.scrs.model.UserModel;
import com.scrs.model.UserRole;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

	private UUID id;
	private String name;
	private String profilePicture;
	private boolean isSuperAdmin;
	private UserRole role;

	public AdminDTO(UserModel user, boolean superAdmin) {
		this.id = user.getId();
		this.name = user.getName();
		this.profilePicture = user.getProfilePicture();
		this.role = user.getRole();
		this.isSuperAdmin = superAdmin;
	}

}
