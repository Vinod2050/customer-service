package com.example.dto;

import lombok.Data;

@Data
public class AddressDto {
	
	private Integer customerId;
	private CurrentAddressDto updateCurrentAddressDto;
	private PermanentAddressDto updatePermanentAddressDto;
}
