package com.iktpreobuka.eDnevnik.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Admin")
@DiscriminatorValue("Admin")
public class AdminEntity extends UserEntity {

	}

	

