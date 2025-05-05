package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentLoanDetails {
	@Id
	private Integer	currentloanId;	
	private Integer	currentloanNo;		
	@OneToOne
	private Emidetails	EMIDetails;		
	private	Double loanAmount;		
	private Integer rateOfInterest;		
	private Integer tenure;	
	private Double	totalAmmountToBePaid;		
	private Integer	processingFees;		
	private	Double totalInterest;		
	private Double sanctionDate;		
	private String remark;		
	private String	status;		

}
