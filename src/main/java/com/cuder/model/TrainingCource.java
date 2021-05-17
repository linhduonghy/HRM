package com.cuder.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingCource implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String content;

	private int duration;

	private int number_of_attendance;

	private String training_cource_name;

	// bi-directional one-to-many association to MemberTraningCource
	@ToString.Exclude
	private List<MemberTrainingCource> memberTraningCources;
}