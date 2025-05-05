package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AddressDto;
import com.example.dto.AddressResponseDto;
import com.example.service.AddressService;

@RestController
@RequestMapping("api/address")
public class CustomerAddressController {
	@Autowired
	private AddressService addressService;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerAddressController.class);

	@PostMapping
    public ResponseEntity<String> addCustomerAddress(@RequestBody AddressDto AddressDto) {
        logger.info("Received request to add customer address: {}", AddressDto);
        String message = addressService.addCustomerAddress(AddressDto);
        logger.info("Successfully added customer address.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
	
	@GetMapping(value = "/getCustomerAddress/{customerId}")
	private ResponseEntity<AddressResponseDto> getCustomerAddress(@PathVariable Integer customerId) {
		 logger.info("Received request to get customer address: {}", customerId);
		AddressResponseDto customerAddress = addressService.getCustomerAddress(customerId);
		if (customerAddress != null) {
			 logger.info("Successfully get customer address.");
			return new ResponseEntity<AddressResponseDto>(customerAddress, HttpStatus.OK);
		}
		else
			return new ResponseEntity<AddressResponseDto>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/deleteCustomerAddress/{customerId}")
	private ResponseEntity<String> deleteCustomerAddress(@PathVariable Integer customerId) {
		String message = addressService.deleteCustomerAddress(customerId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@PatchMapping(value = "/updateCustomerAddress")
	private ResponseEntity<String> updateCustomerAddress(@RequestBody AddressDto addressDto) {
		String message = addressService.updateCustomerAddress(addressDto);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	
}
