package com.example.serviceImpl;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.DocumentDto;
import com.example.entity.Customer;
import com.example.entity.CustomerDocument;
import com.example.repository.CustomerDocumentRepository;
import com.example.repository.CustomerRepository;
import com.example.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private CustomerDocumentRepository customerDocumentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    @Override
    public String addDocument(DocumentDto documentDto, Integer customerId) throws IOException {
        logger.info("Starting document upload for customerId: {}", customerId);

        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            logger.warn("Customer not found for customerId: {}", customerId);
            return "Customer Not Found";
        }

        Customer customer = customerOpt.get();

        // Check if document already exists for this customer
        if (customerDocumentRepository.findByCustomer(customer) != null) {
            logger.warn("Document already exists for customerId: {}", customerId);
            return "Document already exists for this customer.";
        }

        CustomerDocument document = new CustomerDocument();

        // Safe checks for each MultipartFile field
        if (documentDto.getAddharCard() != null && !documentDto.getAddharCard().isEmpty()) {
            document.setAddharCard(documentDto.getAddharCard().getBytes());
        }

        if (documentDto.getAddressProof() != null && !documentDto.getAddressProof().isEmpty()) {
            document.setAddressProof(documentDto.getAddressProof().getBytes());
        }

        if (documentDto.getBankCheque() != null && !documentDto.getBankCheque().isEmpty()) {
            document.setBankCheque(documentDto.getBankCheque().getBytes());
        }

        if (documentDto.getIncomeTax() != null && !documentDto.getIncomeTax().isEmpty()) {
            document.setIncomeTax(documentDto.getIncomeTax().getBytes());
        }

        if (documentDto.getPanCard() != null && !documentDto.getPanCard().isEmpty()) {
            document.setPanCard(documentDto.getPanCard().getBytes());
        }

        if (documentDto.getSignature() != null && !documentDto.getSignature().isEmpty()) {
            document.setSignature(documentDto.getSignature().getBytes());
        }

        if (documentDto.getPhoto() != null && !documentDto.getPhoto().isEmpty()) {
            document.setPhoto(documentDto.getPhoto().getBytes());
        }

        if (documentDto.getSalarySlips() != null && !documentDto.getSalarySlips().isEmpty()) {
            document.setSalarySlips(documentDto.getSalarySlips().getBytes());
        }

        document.setCustomer(customer);
        customerDocumentRepository.save(document);

        logger.info("Document added successfully for customerId: {}", customerId);
        return "Document Added Successfully";
    }

    @Override
    public String updateDocument(DocumentDto documentDto, Integer customerId) throws IOException {
        logger.info("updateDocument called for customerId: {}", customerId);

        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            logger.warn("Customer not found with ID: {}", customerId);
            return "Customer Not Found";
        }

        Customer customer = customerOpt.get();
        CustomerDocument document = customerDocumentRepository.findByCustomer(customer);
        if (document == null) {
            logger.warn("No document found for customerId: {}", customerId);
            return "Document not found for update.";
        }

        boolean documentUpdated = false;

        if (documentDto.getAddharCard() != null) {
            document.setAddharCard(documentDto.getAddharCard().getBytes());
            documentUpdated = true;
        }
        if (documentDto.getAddressProof() != null) {
            document.setAddressProof(documentDto.getAddressProof().getBytes());
            documentUpdated = true;
        }
        if (documentDto.getBankCheque() != null) {
            document.setBankCheque(documentDto.getBankCheque().getBytes());
            documentUpdated = true;
        }
        if (documentDto.getIncomeTax() != null) {
            document.setIncomeTax(documentDto.getIncomeTax().getBytes());
            documentUpdated = true;
        }
        if (documentDto.getPanCard() != null) {
            document.setPanCard(documentDto.getPanCard().getBytes());
            documentUpdated = true;
        }
        if (documentDto.getPhoto() != null) {
            document.setPhoto(documentDto.getPhoto().getBytes());
            documentUpdated = true;
        }
        if (documentDto.getSalarySlips() != null) {
            document.setSalarySlips(documentDto.getSalarySlips().getBytes());
            documentUpdated = true;
        }
        if (documentDto.getSignature() != null) {
            document.setSignature(documentDto.getSignature().getBytes());
            documentUpdated = true;
        }

        if (documentUpdated) {
            customerDocumentRepository.save(document);
            logger.info("Document updated successfully for customerId: {}", customerId);
            return "Document Updated Successfully";
        }

        logger.warn("No documents provided for update for customerId: {}", customerId);
        return "No documents provided for update.";
    }


    @Override
    public String deleteDocument(Integer customerId) {
        logger.info("deleteDocument called for customerId: {}", customerId);

        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            logger.warn("Customer not found for customerId: {}", customerId);
            return "Customer Not Found";
        }

        Customer customer = customerOpt.get();
        CustomerDocument document = customerDocumentRepository.findByCustomer(customer);
        if (document == null) {
            logger.warn("No document found for customerId: {}", customerId);
            return "Document Not Found";
        }

        customerDocumentRepository.delete(document);
        logger.info("Document deleted successfully for customerId: {}", customerId);
        return "Document Deleted Successfully";
    }

    @Override
    public String verifyDocument(Integer customerId) {
        logger.info("verifyDocument called for customerId: {}", customerId);

        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            logger.warn("Customer not found for customerId: {}", customerId);
            return "Customer Not Found";
        }

        Customer customer = customerOpt.get();
        CustomerDocument document = customerDocumentRepository.findByCustomer(customer);
        if (document == null) {
            logger.warn("Document not found for customerId: {}", customerId);
            return "Document Not Found";
        }

        boolean anyUpdated = false;

        if (document.getAddressProof() != null && Boolean.FALSE.equals(document.getAddressProofVerified())) {
            document.setAddressProofVerified(true);
            anyUpdated = true;
            logger.info("AddressProof verified for customerId: {}", customerId);
        }

        if (document.getPanCard() != null && Boolean.FALSE.equals(document.getPanCardVerified())) {
            document.setPanCardVerified(true);
            anyUpdated = true;
            logger.info("PanCard verified for customerId: {}", customerId);
        }

        if (document.getIncomeTax() != null && Boolean.FALSE.equals(document.getIncomeTaxVerified())) {
            document.setIncomeTaxVerified(true);
            anyUpdated = true;
            logger.info("IncomeTax verified for customerId: {}", customerId);
        }

        if (document.getAddharCard() != null && Boolean.FALSE.equals(document.getAddharCardVerified())) {
            document.setAddharCardVerified(true);
            anyUpdated = true;
            logger.info("AddharCard verified for customerId: {}", customerId);
        }

        if (document.getPhoto() != null && Boolean.FALSE.equals(document.getPhotoVerified())) {
            document.setPhotoVerified(true);
            anyUpdated = true;
            logger.info("Photo verified for customerId: {}", customerId);
        }

        if (document.getSignature() != null && Boolean.FALSE.equals(document.getSignatureVerified())) {
            document.setSignatureVerified(true);
            anyUpdated = true;
            logger.info("Signature verified for customerId: {}", customerId);
        }

        if (document.getBankCheque() != null && Boolean.FALSE.equals(document.getBankChequeVerified())) {
            document.setBankChequeVerified(true);
            anyUpdated = true;
            logger.info("BankCheque verified for customerId: {}", customerId);
        }

        if (document.getSalarySlips() != null && Boolean.FALSE.equals(document.getSalarySlipsVerified())) {
            document.setSalarySlipsVerified(true);
            anyUpdated = true;
            logger.info("SalarySlips verified for customerId: {}", customerId);
        }

        if (!anyUpdated) {
            logger.info("All documents already verified or missing for customerId: {}", customerId);
            return "All documents are already verified or missing";
        }

        customerDocumentRepository.save(document);
        logger.info("Document verification updated for customerId: {}", customerId);

        return "Document(s) Verified Successfully";
    }

}
