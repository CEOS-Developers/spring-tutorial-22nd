package com.ceos22.spring_boot;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	private final TestRepository testRepository;

	@Transactional(readOnly=true)
	public List<Test> findAllTests(){
		return testRepository.findAll();
	}

}
