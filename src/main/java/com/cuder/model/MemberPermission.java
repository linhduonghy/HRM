package com.cuder.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	// bi-directional many-to-one association to Member
	private Member member;

	// bi-directional many-to-one association to Permission
	private Permission permission;
}