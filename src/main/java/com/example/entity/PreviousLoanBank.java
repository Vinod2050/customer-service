package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PreviousLoanBank {
	@Id
	private Integer branchId;
	private String branchName;
	private double branchCode;
	private String branchType;
	private String IFSCcode;
	private String MICRcode;
	private Double ConatctNumber;
	private String BankAddress;
	private String Email;
	private String status;

}
