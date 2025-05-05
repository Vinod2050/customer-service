package com.example.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SanctionLetter {
	@Id
	private int sanctionId;
	private Date sanctionDate;
	private String applicantName;
	private Double contactDetails;
	private String producthomeEquity;
	private Double loanAmtSanctioned;
	private String interestType;
	private float rateOfInterest;
	private int loanTenureInMonth;
	private Double monthlyEmiAmount;
	private String modeOfPayment;
	private String remarks;
	private String termsCondition;
	private String status;

}
