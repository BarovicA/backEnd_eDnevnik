package com.iktpreobuka.eDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Component
@Entity(name = "Parent")
@DiscriminatorValue("Parent")
public class ParentEntity extends UserEntity {

@Column
private String email;
	
@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
@JsonIgnore
private List<StudentEntity> student = new ArrayList<>();



public ParentEntity() {
	super();
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public List<StudentEntity> getStudent() {
	return student;
}

public void setStudent(List<StudentEntity> children) {
	this.student = children;
}	


}
