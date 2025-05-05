package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DependentInforamtion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer	dependentInfoId;
	private Integer noOfFamilyMember;
	private Integer noOfChild;
    private	String maritalStatus;
	private String dependentMember;
	private Double familyIncome;
	
	@ManyToOne 
	@JoinColumn(name = "customer_id")  
	private Customer customer;


}
