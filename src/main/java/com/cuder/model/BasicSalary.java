package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicSalary implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private float basic_salary;

	//bi-directional one-to-one association to Salary
	@ToString.Exclude
	private Salary salary;
}