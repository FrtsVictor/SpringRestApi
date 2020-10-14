package com.br.restApi.model;


import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
public class Student extends AbstractEntity {

	private static final long serialVersionUID = -2214876457911181737L;

	
	@NotEmpty(message = "Need be informed")
	private String name;

	@NotEmpty(message = "Need be informed")
	@Email(message = "Email not valid")
	private String email;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	
	
	
}
