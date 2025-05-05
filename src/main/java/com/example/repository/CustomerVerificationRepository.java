package com.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Customer;
import com.example.entity.CustomerVerification;

public interface CustomerVerificationRepository extends JpaRepository<CustomerVerification, Integer> {

    CustomerVerification findByCustomer(Customer customer);
}

