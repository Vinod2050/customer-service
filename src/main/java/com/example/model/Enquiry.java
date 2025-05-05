package com.example.model;

import com.example.entity.Cibil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enquiry {

	private Integer enquiryId;
	private String firstName;
	private String lastName;
	private Integer age;
	private String email;
	private Long mobileNo;
	private String panCardNumber;
	private Cibil cibilDetails;

}
