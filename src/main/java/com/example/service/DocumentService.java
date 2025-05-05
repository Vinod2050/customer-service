package com.example.service;

import java.io.IOException;

import com.example.dto.DocumentDto;

public interface DocumentService {

	  String addDocument(DocumentDto documentDto, Integer customerId) throws IOException;

	    String updateDocument(DocumentDto documentDto, Integer customerId) throws IOException;

	    String deleteDocument(Integer customerId);

	    String verifyDocument(Integer customerId);


}
