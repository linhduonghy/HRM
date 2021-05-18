package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private Date appointed_date;

	private String decription;

	//bi-directional many-to-one association to Manager
	private Manager manager;

	//bi-directional many-to-one association to Staff
	private Staff staff;

	//bi-directional many-to-one association to Title
	private Title title;
}