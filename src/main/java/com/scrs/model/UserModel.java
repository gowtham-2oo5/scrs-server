package com.scrs.model;

import java.util.Date;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "scrs_users")
@DiscriminatorColumn(name = "user_role")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String contact;

    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Date dob;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "user_role", insertable = false, updatable = false)
    private String discriminator;
    

    public UserModel(String name, String username, String email, String password, String contact, UserRole userRole,
                     String profilePicture, Date dob) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.role = userRole;
        this.profilePicture = profilePicture;
        this.dob = dob;
    }


}
