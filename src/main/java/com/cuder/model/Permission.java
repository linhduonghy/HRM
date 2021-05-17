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
public class Permission implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String description;

	private String permission_name;

	// bi-directional one-to-many association to MemberPermission
	@ToString.Exclude
	private List<MemberPermission> memberPermissions;

	public MemberPermission addMemberPermission(MemberPermission memberPermission) {
		getMemberPermissions().add(memberPermission);
		memberPermission.setPermission(this);

		return memberPermission;
	}

	public MemberPermission removeMemberPermission(MemberPermission memberPermission) {
		getMemberPermissions().remove(memberPermission);
		memberPermission.setPermission(null);

		return memberPermission;
	}

}