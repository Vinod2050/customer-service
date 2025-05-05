package com.example.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerVerification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer verificationID;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate verificationDate;
	private Boolean isAllDocumentVerified;
	private Boolean isProfileComplete;
	private String status;
	private String remarks;
	@OneToOne(cascade = CascadeType.ALL)
	private Customer customer;

}
