package com.example.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerDetails {

	@Id
	private Integer CustId;
	private String firstName;
	private String lastName;
	private String panNumber;
    private String enquiryStatus;
    private LocalDate createdDate = LocalDate.now();
    @OneToOne
    private Cibil cibilScore;
}
