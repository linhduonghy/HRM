package com.cuder.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String note;
	
	private Date contract_end_date;

	private Date contract_signing_date;
	
	//bi-directional many-to-one association to ContractType
	private ContractType contractType;

	//bi-directional many-to-one association to Staff
	private Staff staff;

	//bi-directional many-to-one association to Manager
	private Manager manager;

}