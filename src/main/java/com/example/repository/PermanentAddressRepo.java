package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.PermanentAddress;

@Repository
public interface PermanentAddressRepo extends JpaRepository<PermanentAddress, Integer> {

}
