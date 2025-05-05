package com.example.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.dto.CustomerAddressDto;
import com.example.dto.CustomerDto;

import com.example.dto.DependentDto;
import com.example.dto.DocumentDto;
import com.example.entity.Customer;
import com.example.enums.LoanStatus;
import com.example.model.Enquiry;

public interface CustomerService {

	Customer addCustomer(Enquiry enquiry);

	String customerCompleteProfile(CustomerDto customerDto);

	// CustomerAddress addCustomerAddress(CurrentAddress currentAddress,
	// PermanentAddress permanentAddress);


	String deleteCustomer(Integer custmerId);

	Customer getSingleCustomer(Integer customerId);

	List<Customer> getAllCustomers(String firstName, LoanStatus loanStatus, int page, int size, String sortBy);



	String verifyCustomer(Integer customerId);

	String applyloan(Integer customerId);

}
