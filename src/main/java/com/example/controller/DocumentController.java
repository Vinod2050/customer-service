package com.example.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.DocumentDto;
import com.example.service.CustomerService;
import com.example.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
	
	@Autowired
	private DocumentService DocumentService;
	
	private static final Logger logger = LoggerFactory.getLogger(DependentInformationController.class);

	
	@PostMapping("/{customerId}")
	public ResponseEntity<String> addDocument(@ModelAttribute DocumentDto documentDto, @PathVariable Integer customerId) throws IOException {
	    logger.info("Attempting to add document for customerId: {} with document info: {}", customerId, documentDto);

	    String msg = DocumentService.addDocument(documentDto, customerId);

	    logger.info("Document added successfully for customerId: {} - Message: {}", customerId, msg);
	    return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@PatchMapping("/{customerId}")
	public ResponseEntity<String> updateDocument(@ModelAttribute DocumentDto documentDto, @PathVariable Integer customerId) throws IOException {
	    logger.info("Attempting to update document for customerId: {} with updated info: {}", customerId, documentDto);

	    String msg = DocumentService.updateDocument(documentDto, customerId);

	    logger.info("Document updated successfully for customerId: {} - Message: {}", customerId, msg);
	    return new ResponseEntity<>(msg, HttpStatus.OK);
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<String> deleteDocument(@PathVariable Integer customerId) {
	    logger.info("Attempting to delete document for customerId: {}", customerId);

	    String msg = DocumentService.deleteDocument(customerId);

	    logger.info("Document deletion completed for customerId: {} - Message: {}", customerId, msg);
	    return new ResponseEntity<>(msg, HttpStatus.OK);
	}


	@PatchMapping("/verify/{customerId}")
	public ResponseEntity<String>verifyDocument(@PathVariable Integer customerId){
		
		  logger.info("Attempting to delete document for customerId: {}", customerId);
		  String msg = DocumentService.verifyDocument(customerId);
		  logger.info("Document deletion completed for customerId: {} - Message: {}", customerId, msg);
		    return new ResponseEntity<>(msg, HttpStatus.OK); 
	}

}
