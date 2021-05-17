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
public class ContractType implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String contract_type_name;

	private String description;

	// bi-directional many-to-one association to Contract
	@ToString.Exclude
	private List<Contract> contracts;

	public Contract addContract(Contract contract) {
		getContracts().add(contract);
		contract.setContractType(this);

		return contract;
	}

	public Contract removeContract(Contract contract) {
		getContracts().remove(contract);
		contract.setContractType(null);

		return contract;
	}

}