package com.scrs.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "scrs_admins")
@DiscriminatorValue("ROLE_ADMIN")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminModel extends UserModel {

    private boolean isSuperAdmin;

    public AdminModel(String name, String username, String email, String password, String contact,
                      String profilePicture, boolean isSuperAdmin, Date dob) {
        super(name, username, email, password, contact, UserRole.ADMIN, profilePicture, dob); // Call to UserModel constructor
        this.isSuperAdmin = isSuperAdmin;
    }
}
