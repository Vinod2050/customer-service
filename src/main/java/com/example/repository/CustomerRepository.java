package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Customer;
import com.example.enums.LoanStatus;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Page<Customer> findByFirstName(String firstName, Pageable pageable);
	Page<Customer> findByLoanStatus(LoanStatus loanStatus, Pageable pageable);
	Page<Customer> findByFirstNameAndLoanStatus(String firstName, LoanStatus loanStatus, Pageable pageable);


}