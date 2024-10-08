package com.scrs.configs;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.security")
public class UserConfigProperties {

	public static class UserProperty {
		private String username;
		private String password;
		private String role;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

	}

	List<UserProperty> users;

	public List<UserProperty> getUsers() {
		return users;
	}

	public void setUsers(List<UserProperty> users) {
		this.users = users;
	}

}
