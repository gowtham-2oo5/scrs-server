package com.scrs.model;

import java.util.Date;
import java.util.UUID;

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
public abstract class UserModel {

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

	public UserModel() {
	}

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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	// Setter accepts a byte array directly
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public UserRole getUserRole() {
		return role;
	}

	public void setUserRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", contact=" + contact + ", profilePicture=" + profilePicture + ", userRole=" + role
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
