package com.example.service;

import com.example.dto.AddressDto;
import com.example.dto.AddressResponseDto;

public interface AddressService {

	String addCustomerAddress(AddressDto addressDto);

	AddressResponseDto getCustomerAddress(Integer customerId);

	String updateCustomerAddress(AddressDto addressDto);

	String deleteCustomerAddress(Integer customerId);

}
