package com.example.dto;

import lombok.Data;

@Data
public class PermanentAddressDto {
	
//	private Integer permanentAddressId;
	private String areaName;
	private String cityName;
	private String district;
	private String state;
	private Long pincode;
	private Integer houseNumber;
	private String streetName;
    
}
