package com.cuder.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentPosition implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private int number_of_recruitment;

	private RecruitmentTerm recruitmentTerm;

	private Title title;

	@ToString.Exclude
	private List<Candidate> candidates;

}
