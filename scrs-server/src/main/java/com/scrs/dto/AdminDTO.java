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
		this.role = UserRole.valueOf(user.getUserRole());
		this.isSuperAdmin = superAdmin;
	}


	@Override
	public String toString() {
		return "Sending this Admin DATA: [id=" + id + ", name=" + name + ", profilePicture=" + profilePicture
				+ ", isSuperAdmin=" + isSuperAdmin + ", role=" + role + "]";
	}

}
