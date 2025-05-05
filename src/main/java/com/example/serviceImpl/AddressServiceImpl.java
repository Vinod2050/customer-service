package com.example.serviceImpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.controller.DependentInformationController;
import com.example.dto.AddressDto;
import com.example.dto.AddressResponseDto;
import com.example.dto.CurrentAddressDto;
import com.example.dto.PermanentAddressDto;
import com.example.entity.CurrentAddress;
import com.example.entity.Customer;
import com.example.entity.CustomerAddress;
import com.example.entity.PermanentAddress;
import com.example.repository.CurrentAddressRepo;
import com.example.repository.CustomerAddressRepo;
import com.example.repository.CustomerRepository;
import com.example.repository.PermanentAddressRepo;
import com.example.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private PermanentAddressRepo permanentAddressRepo;

	@Autowired
	private CurrentAddressRepo currentAddressRepo;

	@Autowired
	private CustomerAddressRepo customerAddressRepo;

	private static final Logger logger = LoggerFactory.getLogger(DependentInformationController.class);

	@Override
	public String addCustomerAddress(AddressDto addressDto) {

		if (customerRepository.existsById(addressDto.getCustomerId())) {

			Customer customer = customerRepository.findById(addressDto.getCustomerId()).get();

			CustomerAddress customerAddress1 = customerAddressRepo.findByCustomer(customer);

			if (customerAddress1 == null) {

				CustomerAddress customerAddress = new CustomerAddress();

				PermanentAddress permanentAddress = modelMapper.map(addressDto.getUpdatePermanentAddressDto(),
						PermanentAddress.class);

				customerAddress.setPermanentAddress(permanentAddress);

				permanentAddress.setCustomer(customer);

				CurrentAddress currentAddress = modelMapper.map(addressDto.getUpdateCurrentAddressDto(),
						CurrentAddress.class);

				customerAddress.setCurrentAddress(currentAddress);
				customerAddress.setPermanentAddress(permanentAddress);

				currentAddress.setCustomer(customer);

				customerAddress.setCustomer(customer);

				currentAddress.setCustomerAddress(customerAddress);

				permanentAddress.setCustomerAddress(customerAddress);

				permanentAddressRepo.save(permanentAddress);

				currentAddressRepo.save(currentAddress);

				customerAddressRepo.save(customerAddress);

				return "Customer Address (permanent and current address) is added succeessfully for customer id : "
						+ addressDto.getCustomerId();
			}
			return "Customer Address already exists for customer id : " + addressDto.getCustomerId()
					+ " Update/Delete customer address to add..";
		}
		return "Customer is not exists for customer id : " + addressDto.getCustomerId();
	}

	@Override
	public AddressResponseDto getCustomerAddress(Integer customerId) {

		if (customerRepository.existsById(customerId)) {

			Customer customer = customerRepository.findById(customerId).get();

			CustomerAddress customerAddress = customerAddressRepo.findByCustomer(customer);

			AddressResponseDto addressResponseDto1 = new AddressResponseDto();

			if (customerAddress.getCurrentAddress() != null) {

				CurrentAddress currentAddress = customerAddress.getCurrentAddress();

				CurrentAddressDto currentAddressDto = modelMapper.map(currentAddress, CurrentAddressDto.class);

				addressResponseDto1.setCurrentAddress(currentAddressDto);
			}
			if (customerAddress.getPermanentAddress() != null) {

				PermanentAddress permanentAddress = customerAddress.getPermanentAddress();

				PermanentAddressDto permanentAddressDto = modelMapper.map(permanentAddress, PermanentAddressDto.class);

				addressResponseDto1.setPermanentAddress(permanentAddressDto);
			}
			return addressResponseDto1;
		}
		return null;
	}

	@Override
	public String deleteCustomerAddress(Integer customerId) {

		if (customerRepository.existsById(customerId)) {

			Customer customer = customerRepository.findById(customerId).get();

			CustomerAddress existingCustomerAddress = customerAddressRepo.findByCustomer(customer);

			if (existingCustomerAddress.getCurrentAddress() != null) {

				CurrentAddress currentAddress = existingCustomerAddress.getCurrentAddress();

				currentAddress.setAreaName(null);
				currentAddress.setCityName(null);
				currentAddress.getCurrentAddressId();
				currentAddress.setDistrict(null);
				currentAddress.setHouseNumber(null);
				currentAddress.setPincode(null);
				currentAddress.setState(null);
				currentAddress.setStreetName(null);

				currentAddressRepo.save(currentAddress);
			}

			if (existingCustomerAddress.getCurrentAddress() != null) {

				PermanentAddress permanentAddress = existingCustomerAddress.getPermanentAddress();

				permanentAddress.setAreaName(null);
				permanentAddress.setCityName(null);
				permanentAddress.getPermanentAddressId();
				permanentAddress.setDistrict(null);
				permanentAddress.setHouseNumber(null);
				permanentAddress.setPincode(null);
				permanentAddress.setState(null);
				permanentAddress.setStreetName(null);

				permanentAddressRepo.save(permanentAddress);
			}

			return "customer address is deleted successfully for id : " + customerId + " and address id : "
					+ existingCustomerAddress.getCustomerAddressId();
		}
		return "Customer is not existed for id : " + customerId;
	}

	@Override
	public String updateCustomerAddress(AddressDto addressDto) {

		Integer customerId = addressDto.getCustomerId();

		if (customerRepository.existsById(customerId)) {

			Customer customer = customerRepository.findById(customerId).get();

			CustomerAddress existingCustomerAddress = customerAddressRepo.findByCustomer(customer);

			String string1 = null;
			String string2 = null;

			if (addressDto.getUpdateCurrentAddressDto() != null) {

				string1 = " current address/";

				CurrentAddress existingCurrentAddress = existingCustomerAddress.getCurrentAddress();

				CurrentAddressDto currentAddressDto = addressDto.getUpdateCurrentAddressDto();

				if (currentAddressDto.getAreaName() != null) {
					existingCurrentAddress.setAreaName(currentAddressDto.getAreaName());
				}

				if (currentAddressDto.getCityName() != null) {
					existingCurrentAddress.setCityName(currentAddressDto.getCityName());
				}

				if (currentAddressDto.getDistrict() != null) {
					existingCurrentAddress.setDistrict(currentAddressDto.getDistrict());
				}

				if (currentAddressDto.getState() != null) {
					existingCurrentAddress.setState(currentAddressDto.getState());
				}

				if (currentAddressDto.getPincode() != null) {
					existingCurrentAddress.setPincode(currentAddressDto.getPincode());
				}

				if (currentAddressDto.getHouseNumber() != null) {
					existingCurrentAddress.setHouseNumber(currentAddressDto.getHouseNumber());
				}

				if (currentAddressDto.getStreetName() != null) {
					existingCurrentAddress.setStreetName(currentAddressDto.getStreetName());
				}

				CurrentAddress updatedCurrentAddress = currentAddressRepo.save(existingCurrentAddress);

				existingCustomerAddress.setCurrentAddress(updatedCurrentAddress);
			}

			if (addressDto.getUpdatePermanentAddressDto() != null) {

				string2 = "permenant address ";

				PermanentAddress existingPermanentAddress = existingCustomerAddress.getPermanentAddress();

				PermanentAddressDto permanentAddressDto = addressDto.getUpdatePermanentAddressDto();

				if (permanentAddressDto.getAreaName() != null) {
					existingPermanentAddress.setAreaName(permanentAddressDto.getAreaName());
				}

				if (permanentAddressDto.getCityName() != null) {
					existingPermanentAddress.setCityName(permanentAddressDto.getCityName());
				}

				if (permanentAddressDto.getDistrict() != null) {
					existingPermanentAddress.setDistrict(permanentAddressDto.getDistrict());
				}

				if (permanentAddressDto.getState() != null) {
					existingPermanentAddress.setState(permanentAddressDto.getState());
				}

				if (permanentAddressDto.getPincode() != null) {
					existingPermanentAddress.setPincode(permanentAddressDto.getPincode());
				}

				if (permanentAddressDto.getHouseNumber() != null) {
					existingPermanentAddress.setHouseNumber(permanentAddressDto.getHouseNumber());
				}

				if (permanentAddressDto.getStreetName() != null) {
					existingPermanentAddress.setStreetName(permanentAddressDto.getStreetName());
				}

				PermanentAddress updatedPermenantAddress = permanentAddressRepo.save(existingPermanentAddress);

				existingCustomerAddress.setPermanentAddress(updatedPermenantAddress);

				permanentAddressRepo.save(updatedPermenantAddress);
			}
			customerAddressRepo.save(existingCustomerAddress);
			return string1 + " " + string2 + "updated successfully for customer id : " + addressDto.getCustomerId();
		}
		return "Customer Address is not existed to update address details for customer id : "
				+ addressDto.getCustomerId();
	}

}
