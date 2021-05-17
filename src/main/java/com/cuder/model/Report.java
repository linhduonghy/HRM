package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String description;

	private String report_name;

	// bi-directional many-to-one association to Manager
	private Manager manager;

}