package com.example.service;

import java.util.List;

import com.example.dto.DependentDto;

public interface DependentInfoService {

	public String addDepInfo(Integer customerId, DependentDto info);

	public List<DependentDto> getAllDepInfo();

	public String deleteDepInfo(Integer dependentInfoId);

	public DependentDto updateDepInfo(DependentDto dto, Integer dependentInfoId);

	public DependentDto getBydependentInfoId(Integer dependentId);

}
