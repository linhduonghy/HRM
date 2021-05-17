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
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;

	private String department_chief;

	private String department_name;

	private String phone;

	// bi-directional many-to-one association to Company
	private Company company;

	// bi-directional one-to-many association to Staff
	@ToString.Exclude
	private List<Member> members;

	public Member addMember(Member member) {
		getMembers().add(member);
		member.setDepartmant(this);

		return member;
	}

	public Member removeMember(Member member) {
		getMembers().remove(member);
		member.setDepartmant(null);

		return member;
	}

}