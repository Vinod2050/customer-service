package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerDto {
	
	private Integer customerId;
	private Date dateOfBirth;
	private Double requiredTenure;
	private Double totalLoanRequired;
    private Long additionalMobileNo;
    private String gender;
    
}
