package com.example.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.enums.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	private String firstName;
	private String lastName;
	private String customerPanNumber;
	private Integer cibilScore;
	@Temporal(TemporalType.DATE)
	private Date customerDateOfBirth;
	private Integer customerAge;
	private Double requiredTenure;
	private String customerGender;
	private String customerEmail;
	private Long customerMobileNumber;
	private Long additionalMobileNo;
	private Boolean isDeleted;
	private Double customerTotalLoanRequired;
	@Enumerated(EnumType.STRING)
	private LoanStatus loanStatus;
	
}
