package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Degree implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String degree_name;

	private String description;

	private float effectiveTime;

	// bi-directional many-to-one association to Member
	private Member member;
}