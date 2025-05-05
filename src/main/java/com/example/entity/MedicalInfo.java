package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MedicalInfo {
	@Id
	private Integer billId;
	private Integer patientId;
	private String professionsalpatientName;
	private String billingDate;
	private Double loanAmount;
	private String treatment;
	
	
}
