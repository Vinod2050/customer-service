package com.example.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CustomerPersonalDetailsDto {

	private Integer customerId;
	private LocalDate customerDateOfBirth;
	private Double requiredTenure;
	private Double customerTotalLoanRequired;
	private String customerGender;
	private Long customerAdditionalMobileNumber;

}
