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
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	// bi-directional one-to-many association to Appointment
	@ToString.Exclude
	private List<Appointment> appointments;

	// bi-directional one-to-many association to Contract
	@ToString.Exclude
	private List<Contract> contracts;

	// bi-directional one-to-one association to Member
	private Member member;

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setStaff(null);

		return appointment;
	}

}