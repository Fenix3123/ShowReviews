package com.coderscampus.showreviews.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.showreviews.domain.Authorities;
import com.coderscampus.showreviews.repositories.AuthoritiesRepository;

@Service
public class AuthoritiesService {
	@Autowired
	AuthoritiesRepository authoritiesRepo;
	
	public Authorities saveAuthorities(Authorities author) {
		return authoritiesRepo.save(author);
	}
}
