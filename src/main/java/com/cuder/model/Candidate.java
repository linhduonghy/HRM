package com.cuder.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	private String address;

	private Date dob;

	private String email;

	private String idcard;

	private String phone;

	private String sex;

	private String status;

	private RecruitmentPosition recruitmentPosition;
}
