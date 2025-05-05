package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Customer;
import com.example.entity.CustomerDocument;

public interface CustomerDocumentRepository extends JpaRepository<CustomerDocument, Integer>{

	

	CustomerDocument findByCustomer(Customer customer);
}
