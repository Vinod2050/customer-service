package com.example.dto;

import lombok.Data;

@Data
public class CurrentAddressDto {
	
//	private Integer currentAddressId;
	private String areaName;
	private String cityName;
	private String district;
	private String state;
	private Long pincode;
	private Integer houseNumber;
	private String streetName;
    
}
