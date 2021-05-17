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
public class Title implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String description;

	private String title_name;

	// bi-directional one-to-many association to Appointment
	@ToString.Exclude
	private List<Appointment> appointments;

	@ToString.Exclude
	private List<RecruitmentPosition> recruitmentPositions;

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setTitle(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setTitle(null);

		return appointment;
	}

}