package com.cuder.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentTerm implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private String description;

	private String status;

	private Date start_date;

	private Date end_date;

	@ToString.Exclude
	private List<RecruitmentPosition> recruitmentPositions;
}
