package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerDto;

import com.example.entity.Customer;
import com.example.enums.LoanStatus;
import com.example.model.Enquiry;
import com.example.service.CustomerService;

@RestController
@RequestMapping(value = "api/customers")
public class CustomerController {
    
	@Autowired
	private CustomerService customerService;
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@PostMapping
	public ResponseEntity<Customer> addCustomer(@RequestBody Enquiry enquiry) {
		logger.info("Adding new customer with enquiry", enquiry);
		Customer customer = customerService.addCustomer(enquiry);
		logger.info("Customer added successfully", customer.getCustomerId());
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PostMapping("/profile")
	public ResponseEntity<String> customerCompleteProfile(@RequestBody CustomerDto customerDto) {
	    logger.info("Completing profile for customer: {}", customerDto);
	    String customerCompleteProfile = customerService.customerCompleteProfile(customerDto);
	    logger.info("Customer profile completed successfully");
	    return new ResponseEntity<>(customerCompleteProfile, HttpStatus.CREATED);
	}


	
	@DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
        logger.warn("Deleting customer with ID: {}", customerId);
        String msg = customerService.deleteCustomer(customerId);
        logger.info("Customer with ID: {} deleted successfully", customerId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getSingleCustomer(@PathVariable Integer customerId) {
	    logger.info("Fetching customer with ID: {}", customerId);
	    Customer customer = customerService.getSingleCustomer(customerId);
	    
	    if (customer != null) {
	        logger.info("Customer found: {}", customer);
	        return new ResponseEntity<>(customer, HttpStatus.OK);
	    }
	    
	    logger.warn("Customer not found for ID: {}", customerId);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	@GetMapping
	public ResponseEntity<List<Customer>> getCustomers(
	        @RequestParam(required = false) String firstName,
	        @RequestParam(required = false) LoanStatus loanStatus,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "firstName") String sortBy) {
		 logger.info("Fetching customers Details");
	    List<Customer> customerPage = customerService.getAllCustomers(firstName, loanStatus, page, size, sortBy);
	   logger.warn("Error while fetching customers");
	    return ResponseEntity.ok(customerPage);
	}
	
	@PostMapping("verify/{customerId}")
	public ResponseEntity<String> verifyCustomer(@PathVariable Integer customerId) {
	    logger.info("Starting Customer verification for customerId: {}", customerId);

	    String message = customerService.verifyCustomer(customerId);

	    logger.info("Customer verification completed for customerId: {}. Message: {}", customerId, message);
	    return new ResponseEntity<>(message, HttpStatus.OK);
	}

	
 
	@PostMapping("apply/{customerId}")
	public ResponseEntity<String> applyLoan(@PathVariable Integer customerId) {
	    logger.info("Starting Applying Loan  for customerId: {}", customerId);

	    String message = customerService.applyloan(customerId);

	    return new ResponseEntity<>(message, HttpStatus.OK);
	}

	

}
