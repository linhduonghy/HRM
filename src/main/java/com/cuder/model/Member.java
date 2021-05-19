package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String address;

	private Date dob;

	private String email;

	private String idcard;

	private String name;

	private String password;

	private String phone;

	private String sex;

	private String username;
	

	// bi-directional many-to-one association to Departmant
	private Department departmant;

	// bi-directional one-to-many association to Degree
	@ToString.Exclude
	private List<Degree> degrees;

	// bi-directional one-to-one association to Manager
	@ToString.Exclude
	private Manager manager;

	// bi-directional one-to-one association to Staff
	@ToString.Exclude
	private Staff staff;

	// bi-directional one-to-many association to MemberPermission
	@ToString.Exclude
	private List<MemberPermission> memberPermissions;

	// bi-directional many-to-one association to Salary
	private Salary salary;

	// bi-directional one-to-many association to TrainingCourse
	@ToString.Exclude
	private List<MemberTrainingCource> memberTraningCources;

	public MemberPermission addMemberPermission(MemberPermission memberPermission) {
		getMemberPermissions().add(memberPermission);
		memberPermission.setMember(this);

		return memberPermission;
	}

	public MemberPermission removeMemberPermission(MemberPermission memberPermission) {
		getMemberPermissions().remove(memberPermission);
		memberPermission.setMember(null);

		return memberPermission;
	}

	public MemberTrainingCource addMemberTrainingCources(MemberTrainingCource mtc) {
		getMemberTraningCources().add(mtc);
		mtc.setMember(this);

		return mtc;
	}

	public MemberTrainingCource removeMemberTrainingCources(MemberTrainingCource mtc) {
		getMemberTraningCources().remove(mtc);
		mtc.setMember(null);

		return mtc;
	}
}