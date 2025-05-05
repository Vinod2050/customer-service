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
public class Emidetails {
	@Id
	private Integer emiID;
	private Double emiAmountMonthly;	
	private String nextEmiDueDate;	
	private String previousEmiStatus;	

}
