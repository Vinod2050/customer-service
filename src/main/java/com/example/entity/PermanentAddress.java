package com.example.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class PermanentAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer permanentAddressId;
	private String areaName;
	private String cityName;
	private String district;
	private String state;
	private Long pincode;
	private Integer houseNumber;
	private String streetName;
	@ToString.Exclude
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerAddress customerAddress;
	@ToString.Exclude
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;
}