package com.scrs.model;

import java.util.Arrays;

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
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "scrs_users")
@DiscriminatorColumn(name = "user_type")
public abstract class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false, unique = true)
	private String email;
	private String password;
	private String contact;

	@Lob
	private byte[] profilePicture;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	private boolean isOnline;

	public UserModel() {
	}

	public UserModel(String name, String username, String email, String password, String contact,UserRole userRole,
			byte[] profilePicture) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.userRole = userRole;
		this.profilePicture = profilePicture;
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

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", contact=" + contact + ", profilePicture=" + Arrays.toString(profilePicture)
				+ ", userRole=" + userRole + ", isOnline=" + isOnline + "]";
	}

}
