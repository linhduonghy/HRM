package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allowance implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private float levelOfAllowance;

	private String typeAllowance;

	// bi-directional one-to-one association to Salary
	@ToString.Exclude
	private Salary salary;
}