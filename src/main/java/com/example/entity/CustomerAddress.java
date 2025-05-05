package com.example.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerAddressId;

	@OneToOne(cascade = CascadeType.ALL)
	private PermanentAddress permanentAddress;

	@OneToOne(cascade = CascadeType.ALL)
	private CurrentAddress currentAddress;

	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;

}
