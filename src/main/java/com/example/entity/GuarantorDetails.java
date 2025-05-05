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
public class GuarantorDetails {
	@Id
	private Integer GuarantorId;
	private String GuarantorName;
	private String GuarantorDateOfBirth;
	private String GuarantorRelationshipwithCustomer;
	private Long GuarantorMobileNumber;
	private Long GuarantorAdharCardNo;
	private String GuarantorMortgageDetails;
	private String GuarantorJobDetails;
	private String GuarantorLoaclAddress;
	private String GuarantorPermanentAddress;

}
