package com.example.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.controller.DependentInformationController;
import com.example.dto.ApplyLoanDTO;
import com.example.dto.CustomerDto;
import com.example.dto.EmailDTO;
import com.example.entity.Customer;
import com.example.entity.CustomerDocument;
import com.example.entity.CustomerVerification;
import com.example.enums.LoanStatus;
import com.example.feignclient.EmailFeignClient;
import com.example.feignclient.LoanFeignClient;
import com.example.model.Enquiry;
import com.example.repository.CustomerDocumentRepository;
import com.example.repository.CustomerRepository;
import com.example.repository.CustomerVerificationRepository;
import com.example.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailFeignClient emailFeignClient;
	@Autowired
	private LoanFeignClient loanFeignClient;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired 
	private CustomerVerificationRepository customerVerificationRepository ;

	@Autowired
	private CustomerDocumentRepository customerDocumentRepository;

	private static final Logger logger = LoggerFactory.getLogger(DependentInformationController.class);

	@Override
	public Customer addCustomer(Enquiry enquiry) {
		logger.info("Starting customer creation process from enquiry: {}", enquiry);

		Customer customer = new Customer();
		customer.setIsDeleted(false);
		customer.setFirstName(enquiry.getFirstName());
		customer.setLastName(enquiry.getLastName());
		customer.setCustomerAge(enquiry.getAge());
		customer.setCustomerEmail(enquiry.getEmail());
		customer.setCustomerMobileNumber(enquiry.getMobileNo());
		customer.setCustomerPanNumber(enquiry.getPanCardNumber());
		customer.setCibilScore(enquiry.getCibilDetails().getCibilScore());

		Customer savedCustomer = customerRepository.save(customer);

		logger.info("Customer created successfully with customerId: {}", savedCustomer.getCustomerId());
		return savedCustomer;
	}

	@Override
	public String customerCompleteProfile(CustomerDto customerDto) {
		logger.info("Starting customer profile completion for customerId: {}", customerDto.getCustomerId());

		if (customerRepository.existsById(customerDto.getCustomerId())) {
			logger.info("Customer found with ID: {}", customerDto.getCustomerId());

			Customer customerDetails = customerRepository.findById(customerDto.getCustomerId()).get();
			Customer customer = modelMapper.map(customerDetails, Customer.class);

			customer.setCustomerDateOfBirth(customerDto.getDateOfBirth());
			customer.setRequiredTenure(customerDto.getRequiredTenure());
			customer.setCustomerTotalLoanRequired(customerDto.getTotalLoanRequired());
			customer.setAdditionalMobileNo(customerDto.getAdditionalMobileNo());
			customer.setCustomerGender(customerDto.getGender());
			customer.setLoanStatus(LoanStatus.PENDING);

			customerRepository.save(customer);
			logger.info("Customer profile updated and saved successfully for ID: {}", customerDto.getCustomerId());

			EmailDTO emailDTO = new EmailDTO();
			emailFeignClient.sendEmail(emailDTO);
			logger.info("Email notification sent for customerId: {}", customerDto.getCustomerId());

			return "Customer complete profile data is saved successfully..";
		}

		logger.warn("Customer not found for customerId: {}", customerDto.getCustomerId());
		return "Customer in not present for customer id : " + customerDto.getCustomerId();
	}

	

	@Override
	public String deleteCustomer(Integer customerId) {
		logger.info("Attempting to delete customer with ID: {}", customerId);

		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			Customer customer = optionalCustomer.get();
			customer.setIsDeleted(true);
			customerRepository.save(customer);

			logger.info("Customer marked as deleted for ID: {}", customerId);
			return "Customer deleted successfully for ID: " + customerId;
		}

		logger.warn("Customer not found for deletion with ID: {}", customerId);
		return "Customer is not present for ID: " + customerId;
	}

	@Override
	public Customer getSingleCustomer(Integer customerId) {
		logger.info("Fetching customer details for ID: {}", customerId);

		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			logger.info("Customer found for ID: {}", customerId);
			return optionalCustomer.get();
		} else {
			logger.warn("Customer not found for ID: {}", customerId);
			return null;
		}
	}

	@Override
	public List<Customer> getAllCustomers(String firstName, LoanStatus loanStatus, int page, int size, String sortBy) {
		Logger logger = LoggerFactory.getLogger(getClass());

		logger.info(
				"getAllCustomers called with parameters - firstName: {}, loanStatus: {}, page: {}, size: {}, sortBy: {}",
				firstName, loanStatus, page, size, sortBy);

		String[] sortFields = (sortBy != null && !sortBy.isEmpty()) ? sortBy.split(",") : new String[] { "firstName" };
		Sort sort = Sort.by(sortFields);
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Customer> customerPage;

		if (firstName != null && loanStatus != null) {
			logger.info("Fetching customers with firstName: {} and loanStatus: {}", firstName, loanStatus);
			customerPage = customerRepository.findByFirstNameAndLoanStatus(firstName, loanStatus, pageable);
		} else if (firstName != null) {
			logger.info("Fetching customers with firstName: {}", firstName);
			customerPage = customerRepository.findByFirstName(firstName, pageable);
		} else if (loanStatus != null) {
			logger.info("Fetching customers with loanStatus: {}", loanStatus);
			customerPage = customerRepository.findByLoanStatus(loanStatus, pageable);
		} else {
			logger.info("Fetching all customers");
			customerPage = customerRepository.findAll(pageable);
		}

		logger.info("Returning {} customers", customerPage.getContent().size());
		return customerPage.getContent();
	}

	
	@Override
	public String verifyCustomer(Integer customerId) {
	    logger.info("verifyCustomer called for customerId: {}", customerId);

	    // Fetch customer by ID
	    Optional<Customer> customerOpt = customerRepository.findById(customerId);
	    if (customerOpt.isEmpty()) {
	        logger.warn("Customer not found for customerId: {}", customerId);
	        return "Customer Not Found";
	    }

	    Customer customer = customerOpt.get();

	    // Check profile completeness
	    boolean isProfileComplete = isCustomerProfileComplete(customer);
	    if (!isProfileComplete) {
	        logger.warn("Customer profile incomplete for customerId: {}", customerId);
	    }

	    // Check document upload and verification
	    CustomerDocument document = customerDocumentRepository.findByCustomer(customer);
	    if (document == null) {
	        logger.warn("Documents not uploaded for customerId: {}", customerId);
	        return "Customer Documents Not Found";
	    }

	    // Check if all documents are uploaded and verified
	    boolean areDocsUploaded = areAllDocumentsUploaded(document);
	    String documentVerificationStatus = getIsDocumentVerified(document);

	    if (!areDocsUploaded) {
	        logger.warn("Documents incomplete for customerId: {}", customerId);
	        return "All Documents Must Be Uploaded";
	    }

	    if (!"All Documents Verified".equals(documentVerificationStatus)) {
	        logger.warn("Documents not verified yet for customerId: {}", customerId);
	        return documentVerificationStatus;  // Return specific unverified documents
	    }

	    // Update verification record
	    CustomerVerification verification = customerVerificationRepository.findByCustomer(customer);
	    if (verification == null) {
	        verification = new CustomerVerification();
	        verification.setCustomer(customer);
	    }

	    verification.setIsProfileComplete(isProfileComplete);
	    verification.setIsAllDocumentVerified(true);

	    if (isProfileComplete && "All Documents Verified".equals(documentVerificationStatus)) {
	        verification.setStatus("Verified");
	        verification.setRemarks("Profile and documents verified.");
	    } else if (!isProfileComplete) {
	        verification.setStatus("Pending");
	        verification.setRemarks("Profile incomplete.");
	    } else {
	        verification.setStatus("Pending");
	        verification.setRemarks("Documents not verified.");
	    }

	    customerVerificationRepository.save(verification);
	    logger.info("Customer verification saved for customerId: {}", customerId);

	    return "Verified".equals(verification.getStatus())
	            ? "Customer Verified Successfully"
	            : "Customer Verification Pending: " + verification.getRemarks();
	}

	// Helper method to check profile completeness
	private boolean isCustomerProfileComplete(Customer customer) {
	    return customer.getFirstName() != null &&
	           customer.getLastName() != null &&
	           customer.getCustomerPanNumber() != null &&
	           customer.getCustomerDateOfBirth() != null &&
	           customer.getCustomerGender() != null &&
	           customer.getCustomerEmail() != null &&
	           customer.getCustomerMobileNumber() != null &&
	           customer.getCustomerTotalLoanRequired() != null &&
	           customer.getRequiredTenure() != null;
	}

	// Helper method to check if all documents are uploaded
	private boolean areAllDocumentsUploaded(CustomerDocument doc) {
	    return doc.getAddressProof() != null &&
	           doc.getPanCard() != null &&
	           doc.getIncomeTax() != null &&
	           doc.getAddharCard() != null &&
	           doc.getPhoto() != null &&
	           doc.getSignature() != null &&
	           doc.getBankCheque() != null &&
	           doc.getSalarySlips() != null;
	}

	// Helper method to get document verification status
	private String getIsDocumentVerified(CustomerDocument document) {
	    StringBuilder unverifiedDocs = new StringBuilder();

	    // Check for each document's verification status and append to the unverifiedDocs list
	    if (Boolean.FALSE.equals(document.getAddressProofVerified())) {
	        unverifiedDocs.append("Address Proof, ");
	    }
	    if (Boolean.FALSE.equals(document.getPanCardVerified())) {
	        unverifiedDocs.append("Pan Card, ");
	    }
	    if (Boolean.FALSE.equals(document.getIncomeTaxVerified())) {
	        unverifiedDocs.append("Income Tax, ");
	    }
	    if (Boolean.FALSE.equals(document.getAddharCardVerified())) {
	        unverifiedDocs.append("Aadhaar Card, ");
	    }
	    if (Boolean.FALSE.equals(document.getPhotoVerified())) {
	        unverifiedDocs.append("Photo, ");
	    }
	    if (Boolean.FALSE.equals(document.getSignatureVerified())) {
	        unverifiedDocs.append("Signature, ");
	    }
	    if (Boolean.FALSE.equals(document.getBankChequeVerified())) {
	        unverifiedDocs.append("Bank Cheque, ");
	    }
	    if (Boolean.FALSE.equals(document.getSalarySlipsVerified())) {
	        unverifiedDocs.append("Salary Slips, ");
	    }

	    // If any document is unverified, return the list of unverified documents
	    if (unverifiedDocs.length() > 0) {
	    
	        unverifiedDocs.setLength(unverifiedDocs.length() - 2);
	        return "Unverified Documents: " + unverifiedDocs.toString();
	    }

	    return "All Documents Verified";
	}

	@Override
	public String applyloan(Integer customerId) {
	    Optional<CustomerVerification> optional = customerVerificationRepository.findById(customerId);

	    if (optional.isPresent()) {
	        CustomerVerification verification = optional.get();
	        Customer customer = customerRepository.findById(customerId).orElse(null);

	        if (customer != null && "Verified".equalsIgnoreCase(verification.getStatus())) {
	            ApplyLoanDTO loanDto = new ApplyLoanDTO();
	            loanDto.setCustomerId(verification.getCustomer().getCustomerId());
	            loanDto.setCibilScore(customer.getCibilScore());
	            loanDto.setIsCustometrVerified(true);
	            loanDto.setIsDocumentVerified(verification.getIsAllDocumentVerified());

	            loanFeignClient.addApplicant(loanDto);

	            return "Application Data Sent to Loan Service";
	        } else {
	            return "Customer not verified or not found";
	        }
	    } else {
	        return "Verification record not found";
	    }
	}

}
