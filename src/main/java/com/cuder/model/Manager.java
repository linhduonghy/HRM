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
public class Manager implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int years_of_experience;

	//bi-directional many-to-one association to Appointment
	@ToString.Exclude
	private List<Appointment> appointments;

	//bi-directional one-to-many association to Contract
	@ToString.Exclude
	private List<Contract> contracts;

	//bi-directional many-to-one association to Member
	private Member member;

	//bi-directional many-to-one association to Report
	@ToString.Exclude
	private List<Report> reports;

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setManager(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setManager(null);

		return appointment;
	}

	public Report addReport(Report report) {
		getReports().add(report);
		report.setManager(this);

		return report;
	}

	public Report removeReport(Report report) {
		getReports().remove(report);
		report.setManager(null);

		return report;
	}

}