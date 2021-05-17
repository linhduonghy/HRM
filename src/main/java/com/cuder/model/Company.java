package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String company_address;

	private String company_name;

	private String company_phone;

	private String description;

	// bi-directional many-to-one association to Departmant
	@ToString.Exclude
	private List<Department> departmants;

	public Department addDepartmant(Department departmant) {
		getDepartmants().add(departmant);
		departmant.setCompany(this);

		return departmant;
	}

	public Department removeDepartmant(Department departmant) {
		getDepartmants().remove(departmant);
		departmant.setCompany(null);

		return departmant;
	}
}