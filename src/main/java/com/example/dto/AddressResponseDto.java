package com.example.dto;

import lombok.Data;

@Data
public class AddressResponseDto {

	private Integer customerId;
	private PermanentAddressDto permanentAddress;
	private CurrentAddressDto currentAddress;

}
