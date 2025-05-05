package com.example.dto;

import lombok.Data;

@Data
public class UpdateCustomerDto {
	
	private Integer customerId;
	private String firstName;
	private String lastName;
	private Double requiredTenure;
	private String customerEmail;
	private Long mobileNumber;
	private Long additionalMobileNumber;
	private Double customerTotalLoanRequired;
}
