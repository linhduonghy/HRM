package com.cuder.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allowance implements Serializable {
	private static final long serialVersionUID = 1L;

	private String allowance_code;

	private String allowance_name;
	
	private float allowance_value;

	// bi-directional one-to-many association to Salary
	@ToString.Exclude
	private List<Salary> salaries;
}