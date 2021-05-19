package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberTrainingCource implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private Date training_start_date;
	
	// bi-directional many-to-one association to TrainingCourse
	private TrainingCource trainingCource;

	// bi-directional many-to-one association to Member
	private Member member;

}