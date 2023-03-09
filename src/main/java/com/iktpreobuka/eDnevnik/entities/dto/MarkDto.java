package com.iktpreobuka.eDnevnik.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MarkDto {

	
	@NotNull(message = "Mark value can't be null")
	@Min(value = 1, message = "Mark number must be grater then 1")
	@Max(value = 5, message = "Mark number must me less then 5")
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public MarkDto() {
	}
	
	
	
	
}
