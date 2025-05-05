package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dto.ApplyLoanDTO;

@FeignClient(name="loan-service")
public interface LoanFeignClient {
	
	@PostMapping("/api/loans")
	public ResponseEntity<String> addApplicant(@RequestBody ApplyLoanDTO loanDto);


	

}
