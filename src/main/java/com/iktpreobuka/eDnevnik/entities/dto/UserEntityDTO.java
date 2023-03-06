package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.iktpreobuka.eDnevnik.entities.RoleEntity;

public class UserEntityDTO {
	@NotBlank(message = "First name must be provided.")
	@Size(min = 2, max = 30, message = "Name must have between {min} and {max} characters.")
	private String firstName;
	
	@NotNull(message = "Last name must be provided.")
	@Size(min = 2, max = 30, message = "Last name must have between {min} and {max} characters.")
	private String lastName;
	
	@NotNull(message = "Username must be provided.")
	@Size(min = 6, max = 30, message = "Username must have between {min} and {max} characters.")
	private String username;
	
	@NotNull(message = "Password must be provided.")
	@Size(min = 6, max = 30, message = "Password must have between {min} and {max} characters.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password can contain only alphanumeric characters.")
	private String password;
	
	@NotNull(message = "Repeated password must be provided.")
	@Size(min = 6, max = 30, message = "Repeated password must have between {min} and {max} characters.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Repeassword can contain only alphanumeric characters.")
	private String repeatedPassword;
	
	private boolean deleted;
	


	

	public UserEntityDTO() {
	}

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
