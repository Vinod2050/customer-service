package com.example.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.controller.DependentInformationController;
import com.example.dto.DependentDto;
import com.example.entity.Customer;
import com.example.entity.DependentInforamtion;
import com.example.repository.CustomerRepository;
import com.example.repository.DendentInfoRepository;
import com.example.service.DependentInfoService;

@Service
public class DependentServiceImpl implements DependentInfoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DendentInfoRepository dendentInfoRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(DependentInformationController.class);
	
	 
	@Override
	public String addDepInfo(Integer customerId, DependentDto info) {
	    Logger logger = LoggerFactory.getLogger(getClass());

	    logger.info("addDepInfo called for customerId: {}", customerId);

	    Optional<Customer> customerOpt = customerRepository.findById(customerId);

	    if (customerOpt.isPresent()) {
	        logger.info("Customer found with customerId: {}", customerId);

	        Customer customer = customerOpt.get();
	        DependentInforamtion dependentInforamtion = modelMapper.map(info, DependentInforamtion.class);

	        dependentInforamtion.setCustomer(customer);
	        dendentInfoRepository.save(dependentInforamtion);

	        logger.info("Dependent information saved successfully for customerId: {}", customerId);
	        return "Dependent Information saved successfully with ID: " + customerId;
	    } else {
	        logger.warn("Customer with ID {} does not exist.", customerId);
	        return "Customer with ID " + customerId + " does not exist.";
	    }
	}

	@Override
	public List<DependentDto> getAllDepInfo() {
	    logger.info("getAllDepInfo method called");

	    List<DependentInforamtion> entityList = dendentInfoRepository.findAll();
	    logger.info("Fetched {} dependent records from database", entityList.size());

	    List<DependentDto> resultList = new ArrayList<DependentDto>();
	    for (DependentInforamtion info : entityList) {
	        DependentDto dto = modelMapper.map(info, DependentDto.class);
	        resultList.add(dto);
	    }

	    logger.info("Successfully mapped all dependent records to DTOs");
	    return resultList;
	}

	@Override
	public String deleteDepInfo(Integer dependentInfoId) {
	    logger.warn("Attempting to delete Dependent Info with ID: {}", dependentInfoId);

	    if (dendentInfoRepository.existsById(dependentInfoId)) {
	        dendentInfoRepository.deleteById(dependentInfoId);
	        logger.warn("Dependent Info deleted successfully for ID: {}", dependentInfoId);
	        return "Dependent Information Deleted Successfully with Id " + dependentInfoId;
	    }

	    logger.warn("Dependent Info with ID {} does not exist", dependentInfoId);
	    return "Id Does Not Exist To Delete the Dependent Information";
	}

	@Override
	public DependentDto updateDepInfo(DependentDto dto, Integer dependentInfoId) {
	    logger.info("Attempting to update Dependent Info with ID: {}", dependentInfoId);

	    Optional<DependentInforamtion> optional = dendentInfoRepository.findById(dependentInfoId);

	    if (!optional.isPresent()) {
	        logger.error("Dependent Info not found with ID: {}", dependentInfoId);
	        throw new RuntimeException("Dependent Info not found with ID: " + dependentInfoId);
	    }

	    DependentInforamtion existingInfo = optional.get();

	    if (dto.getNoOfFamilyMember() != null) {
	        existingInfo.setNoOfFamilyMember(dto.getNoOfFamilyMember());
	    }
	    if (dto.getNoOfChild() != null) {
	        existingInfo.setNoOfChild(dto.getNoOfChild());
	    }
	    if (dto.getMaritalStatus() != null) {
	        existingInfo.setMaritalStatus(dto.getMaritalStatus());
	    }
	    if (dto.getDependentMember() != null) {
	        existingInfo.setDependentMember(dto.getDependentMember());
	    }
	    if (dto.getFamilyIncome() != null) {
	        existingInfo.setFamilyIncome(dto.getFamilyIncome());
	    }

	    DependentInforamtion updatedInfo = dendentInfoRepository.save(existingInfo);
	    logger.info("Dependent Info updated successfully for ID: {}", dependentInfoId);

	    return modelMapper.map(updatedInfo, DependentDto.class);
	}

	@Override
	public DependentDto getBydependentInfoId(Integer dependentId) {
	    logger.info("Fetching Dependent Info by ID: {}", dependentId);

	    DependentInforamtion dependentInformation = dendentInfoRepository.findById(dependentId)
	            .orElse(null);

	    if (dependentInformation == null) {
	        logger.warn("No Dependent Info found for ID: {}", dependentId);
	        return null;
	    }

	    logger.info("Dependent Info found for ID: {}", dependentId);
	    return modelMapper.map(dependentInformation, DependentDto.class);
	}





}
