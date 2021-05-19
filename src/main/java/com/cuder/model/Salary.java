package com.cuder.model;

import java.io.Serializable;
import java.sql.Date;

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

	private float salary;

	private Date createdDate;
	
	private float coefficientBasicSalary;
	// bi-directional one-to-one association to Allowance
	private Allowance allowance;

	// bi-directional one-to-one association to BasicSalary
	private BasicSalary basicSalary;

	// bi-directional many-to-one association to Member
	@ToString.Exclude
	private Member member;

}