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
public class LoanDisbursement {
	@Id
	private Integer agreementId;
	private Integer loanNo;
	private String agreementDate;
	private String amountPayType;
	private Double totalAmount;
	private String bankName;
	private Long accountNumber;
	private String IFSCCode;
	private String accountType;
	private Double transferAmount;
	private String paymentStatus;
	private String amountPaidDate;

}
