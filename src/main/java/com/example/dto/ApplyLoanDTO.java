package com.example.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.entity.Customer;

import lombok.Data;

@Data
public class ApplyLoanDTO {
	
	private Integer customerId;
	private String firstName;
	private String lastName;
	private String customerEmail;
	private Integer cibilScore;
	private Boolean IsDocumentVerified ;
	private Boolean IsCustometrVerified;

}
