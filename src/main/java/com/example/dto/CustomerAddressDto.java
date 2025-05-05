package com.example.dto;

import com.example.entity.CurrentAddress;
import com.example.entity.PermanentAddress;

import lombok.Data;

@Data
public class CustomerAddressDto {
	private Integer customerID;
	private CurrentAddress currentAddress;
	private PermanentAddress permanentAddress;

}
