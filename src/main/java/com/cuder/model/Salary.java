package com.cuder.model;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String jobType;

	private float salary;

	// bi-directional one-to-one association to Allowance
	private Allowance allowance;

	// bi-directional one-to-one association to BasicSalary
	private BasicSalary basicSalary;

	// bi-directional one-to-one association to Bonus
	private Bonus bonus;

	// bi-directional many-to-one association to Member
	@ToString.Exclude
	private Member member;

}