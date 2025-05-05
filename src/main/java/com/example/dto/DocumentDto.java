package com.example.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class DocumentDto {
	
	private MultipartFile addressProof;
	
	private MultipartFile panCard;
	
	private MultipartFile IncomeTax;
	
	private MultipartFile addharCard;
	
	private MultipartFile photo;
	
	private MultipartFile signature;
	
	private MultipartFile bankCheque;
	
	private MultipartFile salarySlips;

}
