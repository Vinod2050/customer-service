package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.DependentDto;
import com.example.service.DependentInfoService;

@RestController
@RequestMapping(value = "api/dependents")
public class DependentInformationController {
	
	@Autowired
	private DependentInfoService dependentInfoService;
	
	private static final Logger logger = LoggerFactory.getLogger(DependentInformationController.class);

	@PostMapping("/{customerId}")
	public ResponseEntity<String>addDepInfo(@PathVariable Integer customerId ,@RequestBody DependentDto info)
	{
		logger.info("Adding dependent info for customerId: {}", customerId);
		String res=dependentInfoService.addDepInfo(customerId,info); 
		logger.info("Dependent added successfully");
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	@GetMapping("/{dependentId}")
	public ResponseEntity<DependentDto> getDependentInfo(@PathVariable Integer dependentId) {
	    logger.info("Fetching dependent info for dependentId: {}", dependentId);

	    DependentDto dependentDto = dependentInfoService.getBydependentInfoId(dependentId);
	    
	    if (dependentDto != null) {
	        logger.info("Fetched dependent info successfully.");
	        return ResponseEntity.ok(dependentDto);
	    } else {
	        logger.warn("No dependent info found for ID: {}", dependentId);
	        return ResponseEntity.noContent().build();  
	    }
	}

	@GetMapping
	public ResponseEntity<List<DependentDto>> getAllDepInfo() {
	    logger.info("Fetching all dependent info");

	    List<DependentDto> infoList = dependentInfoService.getAllDepInfo();

	    if (infoList != null && !infoList.isEmpty()) {
	        logger.info("Fetched all dependent info successfully.");
	        return ResponseEntity.ok(infoList);
	    } else {
	        logger.warn("No dependent info found.");
	        return ResponseEntity.noContent().build();  
	    }
	}

	@DeleteMapping("/{dependentInfoId}")
	public ResponseEntity<String> deleteDepInfo(@PathVariable Integer dependentInfoId) {
	    logger.info("Attempting to delete dependent info with ID: {}", dependentInfoId);
	    
	    String msg = dependentInfoService.deleteDepInfo(dependentInfoId);
	    
	    logger.info("Deletion result for dependent ID {}: {}", dependentInfoId, msg);
	    return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@PatchMapping("/{dependentInfoId}")
	public ResponseEntity<DependentDto> updateDepInfo(@RequestBody DependentDto dto, @PathVariable Integer dependentInfoId) {
	    logger.info("Attempting to update dependent info with ID: {} and payload: {}", dependentInfoId, dto);

	    DependentDto updatedInfo = dependentInfoService.updateDepInfo(dto, dependentInfoId);

	    logger.info("Successfully updated dependent info for ID: {}", dependentInfoId);
	    return new ResponseEntity<>(updatedInfo, HttpStatus.OK);
	}

		
}
