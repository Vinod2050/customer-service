package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PreviousLoan {
	@Id
	private Integer previousLoanId;
	private Double previousLoanAmount;
	private Integer previousLoanTenure;
	private Double previousLoanpaidAmount;
	private Double previousLoanremainingAmount;
	private Integer previousLoandeafulterCount;
	@OneToOne
	private PreviousLoanBank previousLoanbankDetails;
	private String previousLoanStatus;
	private String previousLoanRemark;

}
